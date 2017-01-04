package com.example.bugdroid.lolquests.Objects;


/**
 * Created by BugDroid on 05/12/2016.
 */

public class Quest {
    private static final Quest INSTANCE = new Quest();

    int id;
    int QuestType;
    String desc;
    int progress;
    int csGoal;
    int Exp;

    public Quest () {

    }

    public Quest (int QuestType, String desc, int progress, int csGoal, int Exp) {
        this.QuestType = QuestType;
        this.desc = desc;
        this.progress = progress;
        this.csGoal = csGoal;
        this.Exp = Exp;
    }

    public Quest (int id, int QuestType, String desc, int progress, int csGoal, int Exp) {
        this.id = id;
        this.QuestType = QuestType;
        this.desc = desc;
        this.progress = progress;
        this.csGoal = csGoal;
        this.Exp = Exp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestType() {
        return QuestType;
    }

    public void setQuestType(int questType) {
        QuestType = questType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCsGoal() {
        return csGoal;
    }

    public void setCsGoal(int csGoal) {
        this.csGoal = csGoal;
    }

    public int getExp() {
        return Exp;
    }

    public void setExp(int exp) {
        Exp = exp;
    }

    public static Quest getInstance() {
        return INSTANCE;
    }
}
