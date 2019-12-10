
package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("selected_note")
    @Expose
    private int selectedNote;
    @SerializedName("notes")
    @Expose
    private List<Note> notes = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(int selectedNote) {
        this.selectedNote = selectedNote;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
