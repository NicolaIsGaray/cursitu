package pdevs.CursITU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdevs.CursITU.models.ClassroomEntity;
import pdevs.CursITU.models.SubjectsEntity;
import pdevs.CursITU.repositories.ClassroomRepo;
import pdevs.CursITU.repositories.SubjectsRepo;

import java.util.List;

@RestController
@RequestMapping("/cursitu-app")
public class MainController {

    @Autowired
    private SubjectsRepo subjectsRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    @GetMapping("/materias")
    public List<SubjectsEntity> getSubjects() {
        return (List<SubjectsEntity>) subjectsRepo.findAll();
    }

    @GetMapping("/materias/{id}")
    public SubjectsEntity getSubjectByID(@PathVariable long id) {
        return this.subjectsRepo.findById(id).orElse(null);
    }

    @GetMapping("/cursos")
    public List<ClassroomEntity> getClassrooms() { return (List<ClassroomEntity>) classroomRepo.findAll(); }
}
