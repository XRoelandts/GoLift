package com.example.golift.workout;

public class Exercise
{
    private String name;
    private String type;
    private String muscle;
    private String difficulty;
    private String instruction;
    private String equipment;
    //private String safety_info;

    public Exercise() {

    }
    public Exercise(String name, String type, String muscle, String difficulty, String instruction, String equipments, String safety_info) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.difficulty = difficulty;
        this.instruction = instruction;
        this.equipment = equipment;
        //this.safety_info = safety_info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipments) {
        this.equipment = equipments;
    }

    /*
    public String getSafety_info() {
        return safety_info;
    }

    public void setSafety_info(String safety_info) {
        this.safety_info = safety_info;
    }

     */
}

