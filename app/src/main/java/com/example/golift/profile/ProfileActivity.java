package com.example.golift.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.golift.R;
import com.example.golift.pageButtonsFragment;
import com.example.golift.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private boolean isEditMode = false;
    private Uri selectedPhotoUri;

    private final ActivityResultLauncher<Intent> photoPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedPhotoUri = result.getData().getData();
                    Glide.with(this).load(selectedPhotoUri).into(binding.profileImage);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbarAndBottomNav(savedInstanceState);
        loadUserProfile();

        binding.tvName.setOnClickListener(v -> enterEditMode());
        binding.btnChangePhoto.setOnClickListener(v -> openGallery());
        binding.btnCancel.setOnClickListener(v -> exitEditMode());
        binding.btnSave.setOnClickListener(v -> saveProfileChanges());
    }

    private void setupToolbarAndBottomNav(Bundle savedInstanceState) {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.button2.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.buttonFragments, new pageButtonsFragment(), "buttonsFrag")
                    .commit();
        }
    }

    private void loadUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            finish();
            return;
        }

        // Name
        String name = user.getDisplayName();
        if (name != null && !name.isEmpty()) {
            binding.tvName.setText(name);
            binding.etName.setText(name);
        } else {
            // Make sure this string exists in your res/values/strings.xml
            binding.tvName.setText(R.string.profile_tap_to_set_name);
        }

        // Photo
        loadProfileImage();
    }

    /**
     * FIX: Created a separate method to only load the profile image.
     * This prevents unnecessary reloading of other profile data.
     */
    private void loadProfileImage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            Glide.with(this).load(user.getPhotoUrl()).into(binding.profileImage);
        } else {
            // Optional: Set a default image if no photo exists
            binding.profileImage.setImageResource(R.drawable.ic_profile_placeholder); // Example: replace with your default drawable
        }
    }

    private void enterEditMode() {
        isEditMode = true;
        binding.tvName.setVisibility(View.GONE);
        binding.tilName.setVisibility(View.VISIBLE);
        binding.etName.setText(binding.tvName.getText());
        binding.etName.requestFocus();
        binding.etName.setSelection(binding.etName.getText().length());
        binding.btnChangePhoto.setVisibility(View.VISIBLE);
        binding.layoutEditButtons.setVisibility(View.VISIBLE);
    }

    private void exitEditMode() {
        isEditMode = false;
        binding.tvName.setVisibility(View.VISIBLE);
        binding.tilName.setVisibility(View.GONE);
        binding.btnChangePhoto.setVisibility(View.GONE);
        binding.layoutEditButtons.setVisibility(View.GONE);

        // FIX: Revert the image without reloading the user's name.
        if (selectedPhotoUri != null) {
            selectedPhotoUri = null;
            // Revert image to original from Firebase by only loading the image
            loadProfileImage();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPicker.launch(intent);
    }

    private void saveProfileChanges() {
        setSavingState(true); // Disable buttons
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            setSavingState(false);
            return;
        }

        String newName = binding.etName.getText().toString().trim();
        UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder().setDisplayName(newName);

        if (selectedPhotoUri != null) {
            // Upload photo first
            StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("profile_photos/" + user.getUid() + ".jpg");
            photoRef.putFile(selectedPhotoUri)
                    .addOnSuccessListener(taskSnapshot -> photoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        request.setPhotoUri(uri);
                        updateFirebaseProfile(request.build());
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Photo upload failed", Toast.LENGTH_SHORT).show();
                        setSavingState(false);
                    });
        } else {
            // Only update the name if it has changed
            String currentName = user.getDisplayName() != null ? user.getDisplayName() : "";
            if (!newName.equals(currentName)) {
                updateFirebaseProfile(request.build());
            } else {
                setSavingState(false);
                exitEditMode();
            }
        }
    }

    private void updateFirebaseProfile(UserProfileChangeRequest request) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            setSavingState(false);
            return;
        }

        user.updateProfile(request).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
                loadUserProfile();
                exitEditMode();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
            setSavingState(false);
        });
    }

    private void setSavingState(boolean isSaving) {
        binding.btnSave.setEnabled(!isSaving);
        binding.btnCancel.setEnabled(!isSaving);
    }
}


