package org.anchorz.java_drive.mappers;
import org.anchorz.java_drive.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO notes (note, user_id) VALUES (#{note}, #{user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createNote(Note note);

    @Select("SELECT * FROM notes WHERE user_id=#{user_id}")
    List<Note> getAllNotes(int user);

    @Update("UPDATE notes SET note=#{note} WHERE user_id=#{user_id}")
    void updateNote(Note note);

    @Delete("DELETE FROM notes WHERE id=#{note_id}")
    void deleteNote(int note_id);
}
