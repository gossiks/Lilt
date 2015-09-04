package org.kazin.lilt.managers;

/**
 * Created by Alexey on 04.09.2015.
 */
public class ProgressLoadingMan {
    private int progressCount = 0;
    private int errors;
    private int totalProgressCount;

    public ProgressLoadingMan(int totalProgressCount) {
        this.totalProgressCount = totalProgressCount;
    }

    public void addProgress(){
        progressCount++;
    }

    public void addError(){
        errors++;
    }

    public int getProgress(){
        return progressCount+errors;
    }

    public String getFinalProgress(){
        return progressCount+"/"+totalProgressCount;
    }
}
