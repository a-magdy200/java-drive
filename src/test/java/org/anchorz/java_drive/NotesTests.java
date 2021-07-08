package org.anchorz.java_drive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NotesTests {
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;
    @FindBy(id = "add_new_note")
    private WebElement addNewNoteFormButton;
    @FindBy(id = "addNoteBtn")
    private WebElement addNoteBtn;
    @FindBy(id = "title")
    private WebElement titleField;
    @FindBy(id = "description")
    private WebElement descriptionField;
    @FindBy(className = "note-row")
    private List<WebElement> notes;
    @FindBy(css = ".note-row .btn-info")
    private List<WebElement> editBtns;
    @FindBy(css = ".note-row .btn-danger")
    private List<WebElement> deleteBtns;
    @FindBy(css = ".note-row .title")
    private List<WebElement> notesTitles;
    @FindBy(css = ".note-row .description")
    private List<WebElement> notesDescriptions;

    public NotesTests(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void changeTab() {
        navNotesTab.click();
    }
    public void createNote(String title, String description) {
        addNewNoteFormButton.click();
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        addNoteBtn.click();
    }
    public String getNoteTitle(int noteIndex) {
        return notesTitles.get(noteIndex).getText();
    }
    public String getNoteDescription(int noteIndex) {
        return notesDescriptions.get(noteIndex).getText();
    }
    public void editNote(int noteIndex, String title, String description) {
        editBtns.get(noteIndex).click();
        titleField.clear();
        titleField.sendKeys(title);
        descriptionField.clear();
        descriptionField.sendKeys(description);
        addNoteBtn.click();
    }
    public void deleteNote(int noteIndex) {
        changeTab();
        deleteBtns.get(noteIndex).click();
    }
    public int notesCount() {
        return notes.size();
    }
}
