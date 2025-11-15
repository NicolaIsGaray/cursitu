package pdevs.CursITU.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdevs.CursITU.controller.request.GroupCreateDTO;
import pdevs.CursITU.models.GroupEntity;
import pdevs.CursITU.models.InvitationsEntity;
import pdevs.CursITU.models.UserEntity;
import pdevs.CursITU.repositories.GroupRepo;
import pdevs.CursITU.service.group.GroupService;
import pdevs.CursITU.service.user.StudentService;

import java.util.List;

@RestController
@RequestMapping("/cursitu-app")
public class GroupsController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepo groupRepo;

    @GetMapping("/grupos")
    public List<GroupEntity> getGroups() {
        return (List<GroupEntity>) groupRepo.findAll();
    }

    @PostMapping("/crear-grupo")
    public ResponseEntity<Object> createGroup(@RequestBody GroupCreateDTO dto) {
        try {
            GroupEntity nuevoGrupo = groupService.createGroupFromDto(dto);
            return ResponseEntity.ok(nuevoGrupo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{id}/invitaciones")
    public ResponseEntity<UserEntity> addInvitacion(
            @PathVariable Long id,
            @RequestBody InvitationsEntity invitation
    ) {
        UserEntity updated = studentService.addInvitacion(id, invitation);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/invitaciones/{groupId}/aceptar")
    public ResponseEntity<UserEntity> acceptInvitacion(@PathVariable Long id, @PathVariable Long groupId) {
        UserEntity updated = studentService.acceptInvitacion(id, groupId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/invitaciones/{groupId}/rechazar")
    public ResponseEntity<UserEntity> rejectInvitation(@PathVariable Long id, @PathVariable Long groupId) {
        UserEntity updated = studentService.rejectInvitacion(id, groupId);
        return ResponseEntity.ok(updated);
    }
}
