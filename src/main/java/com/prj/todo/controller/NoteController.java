package com.prj.todo.controller;

import com.prj.todo.model.Note;
import com.prj.todo.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteRepository noteRepository;

    public NoteController(NoteRepository nr) {
        this.noteRepository = nr;
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note noteRequest) {
        if(noteRequest.getTitle() == null || noteRequest.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // 400 Bad request
        }

        try {
            Note savedNote = noteRepository.save(noteRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
        } catch (Exception e) {
            System.err.println("Error saving note: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        try {
            List<Note> notes = noteRepository.findAll();
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            System.err.println("Error fetching notes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        try {
            Optional<Note> optionalNote = noteRepository.findById(id);
            return optionalNote.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("Error fetching note by ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
