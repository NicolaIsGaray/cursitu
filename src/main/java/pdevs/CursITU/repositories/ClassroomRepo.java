package pdevs.CursITU.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pdevs.CursITU.models.ClassroomEntity;

@Repository
public interface ClassroomRepo extends JpaRepository<ClassroomEntity, Long> {
    @Modifying
    @Query(value = "DELETE FROM curso_alumno WHERE curso_id = :classroomId AND alumno_id = :studentId", nativeQuery = true)
    void removeStudentFromClassroom(@Param("classroomId") Long classroomId, @Param("studentId") Long studentId);

    @Modifying
    @Query(value = "DELETE FROM curso_profesor WHERE curso_id = :classroomId AND profesor_id = :teacherId", nativeQuery = true)
    void removeTeacherFromClassroom(@Param("classroomId") Long classroomId, @Param("teacherId") Long teacherId);
}