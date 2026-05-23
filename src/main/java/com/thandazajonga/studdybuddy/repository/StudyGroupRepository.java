package com.thandazajonga.studdybuddy.repository;

import com.thandazajonga.studdybuddy.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM StudyGroup g JOIN g.members m " +
            "WHERE g.id = :groupId AND m.id = :userId")
    boolean isUserAlreadyMember(Integer groupId, Integer userId);
    List<StudyGroup> findByCourseCodeContainingIgnoreCase(String courseCode);
    List<StudyGroup> findByStudyGroupNameContainingIgnoreCase(String name);
}
