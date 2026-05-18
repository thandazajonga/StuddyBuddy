package com.thandazajonga.studdybuddy.controller;

import com.thandazajonga.studdybuddy.dto.CreateGroupRequest;
import com.thandazajonga.studdybuddy.dto.CreateGroupResponse;
import com.thandazajonga.studdybuddy.dto.StudyGroupSummaryResponse;
import com.thandazajonga.studdybuddy.entity.StudyGroup;
import com.thandazajonga.studdybuddy.service.StudyGroupService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study_groups")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    public StudyGroupController(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }
    @PostMapping("/create")
    public CreateGroupResponse createStudyGroup(@RequestBody CreateGroupRequest createGroupRequest, Authentication authentication) {
        String email = authentication.getName();
        return studyGroupService.createStudyGroup(createGroupRequest,email);
    }
    @GetMapping
    public List<StudyGroupSummaryResponse> getAllStudyGroups() {
        return studyGroupService.getAllStudyGroups();
    }
}
