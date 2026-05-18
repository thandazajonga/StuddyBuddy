package com.thandazajonga.studdybuddy.service;

import com.thandazajonga.studdybuddy.dto.CreateGroupRequest;
import com.thandazajonga.studdybuddy.dto.CreateGroupResponse;
import com.thandazajonga.studdybuddy.dto.StudyGroupSummaryResponse;
import com.thandazajonga.studdybuddy.entity.StudyGroup;
import com.thandazajonga.studdybuddy.entity.User;
import com.thandazajonga.studdybuddy.repository.StudyGroupRepository;
import com.thandazajonga.studdybuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final UserRepository userRepository;

    public StudyGroupService(StudyGroupRepository studyGroupRepository, UserRepository userRepository) {
        this.studyGroupRepository = studyGroupRepository;
        this.userRepository = userRepository;
    }
    public List<StudyGroupSummaryResponse> getAllStudyGroups() {
        List<StudyGroup> studyGroups = studyGroupRepository.findAll();
        return studyGroups.stream().map(studyGroup -> {
            StudyGroupSummaryResponse response = new StudyGroupSummaryResponse();
            response.setStudyGroupId(studyGroup.getStudyGroupId());
            response.setStudyGroupName(studyGroup.getStudyGroupName());
            response.setDescription(studyGroup.getDescription());
            response.setCourseCode(studyGroup.getCourseCode());

            response.setMaxMembers(studyGroup.getMaxMembers());

            response.setCurrentMembers(studyGroup.getMembers().size());

            response.setOwnerName(studyGroup.getOwner().getName());
            return response;
        }).toList();
    }

    public CreateGroupResponse createStudyGroup(CreateGroupRequest createGroupRequest,String email) {
        //creator of the group is the owner and the first member of the group
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setStudyGroupName(createGroupRequest.getName());
        studyGroup.setCourseCode(createGroupRequest.getCourseCode());
        studyGroup.setMaxMembers(createGroupRequest.getMaxMembers());
        studyGroup.setDescription(createGroupRequest.getDescription());
        studyGroup.setCreatedAt(LocalDateTime.now());

        //set owner
        studyGroup.setOwner(user);
        //owner joins group automatically
        studyGroup.getMembers().add(user);

        StudyGroup savedGroup= studyGroupRepository.save(studyGroup);
        CreateGroupResponse response = new CreateGroupResponse();

        response.setStudyGroupId(savedGroup.getStudyGroupId());
        response.setStudyGroupName(savedGroup.getStudyGroupName());
        response.setDescription(savedGroup.getDescription());
        response.setCourseCode(savedGroup.getCourseCode());
        response.setMaxMembers(savedGroup.getMaxMembers());
        response.setCreatedAt(savedGroup.getCreatedAt());

        response.setOwnerName(savedGroup.getOwner().getName());

        List<String> memberNames= savedGroup.getMembers()
                .stream().map(User::getName)
                .toList();
        response.setMembers(memberNames);
        return response;
    }
}
