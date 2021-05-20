package org.anchorz.java_drive.mappers;
import org.anchorz.java_drive.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO notes (title, description, userId) VALUES (#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Select("SELECT * FROM notes WHERE userId=#{userId}")
    List<Note> getAllNotes(int userId);

    @Select("SELECT * FROM notes WHERE noteId=#{noteId}")
    Note getNote(int noteId);

    @Update("UPDATE notes SET title=#{title}, description=#{description} WHERE noteId=#{noteId}")
    void updateNote(Note note);

    @Delete("DELETE FROM notes WHERE noteId=#{noteId}")
    void deleteNote(int noteId);
}
