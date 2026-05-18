package com.thandazajonga.studdybuddy.controller;

import com.thandazajonga.studdybuddy.dto.CreateGroupRequest;
import com.thandazajonga.studdybuddy.dto.CreateGroupResponse;
import com.thandazajonga.studdybuddy.entity.StudyGroup;
import com.thandazajonga.studdybuddy.service.StudyGroupService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
