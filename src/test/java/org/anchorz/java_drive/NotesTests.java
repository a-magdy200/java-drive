package org.anchorz.java_drive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NotesTests {
    @FindBy(id = "add_new_note")
    private WebElement addNewNoteButton;
    @FindBy(id = "addNoteBtn")
    private WebElement addNoteBtn;
    @FindBy(id = "title")
    private WebElement titleField;
    @FindBy(id = "description")
    private WebElement descriptionField;
    @FindBy(className = "note-row")
    private List<WebElement> notes;

    public NotesTests(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void createNote(String title, String description) {
        addNewNoteButton.click();
        titleField.sendKeys(title);
        descriptionField.sendKeys(description);
        addNoteBtn.click();
    }
    public int notesCount() {
        return notes.size();
    }
}
