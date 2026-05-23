package com.thandazajonga.studdybuddy.service;

import com.thandazajonga.studdybuddy.dto.CreateGroupRequest;
import com.thandazajonga.studdybuddy.dto.CreateGroupResponse;
import com.thandazajonga.studdybuddy.dto.StudyGroupSummaryResponse;
import com.thandazajonga.studdybuddy.entity.StudyGroup;
import com.thandazajonga.studdybuddy.entity.User;
import com.thandazajonga.studdybuddy.repository.StudyGroupRepository;
import com.thandazajonga.studdybuddy.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public String joinStudyGroup(Integer studyGroupId, String email) {
        //Find the group
        StudyGroup studyGroup= studyGroupRepository.findById(studyGroupId).orElseThrow(()-> new RuntimeException("Study Group not found"));
        // Find the user
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));
        //Check if the user is already a member
        if (studyGroupRepository.isUserAlreadyMember(studyGroupId, user.getId())) {
            throw new RuntimeException(
                    "User already joined this group"
            );
        }
        //Check if max has been met
        if(studyGroup.getMembers().size()>= studyGroup.getMaxMembers()){
            throw new RuntimeException("Study group is full");
        }
        studyGroup.getMembers().add(user);
        studyGroupRepository.save(studyGroup);
        return "Successfully joined study group!";
    }
    @Transactional
    public String deleteStudyGroup(Integer studyGroupId, String email) {
        StudyGroup studyGroup=studyGroupRepository.findById(studyGroupId).orElseThrow(()->new RuntimeException("Study Group not found"));
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
       if(!studyGroup.getOwner().getId().equals(user.getId())){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only the owner can delete a study group");
       }
       studyGroupRepository.delete(studyGroup);
       return "Successfully deleted study group!";
    }
    public StudyGroupSummaryResponse getStudyGroupSummary(Integer studyGroupId, String email) {
        StudyGroup studyGroup=studyGroupRepository.findById(studyGroupId).orElseThrow(()-> new RuntimeException("Study Group not found"));
        StudyGroupSummaryResponse response = new StudyGroupSummaryResponse();
        response.setStudyGroupId(studyGroup.getStudyGroupId());
        response.setStudyGroupName(studyGroup.getStudyGroupName());
        response.setDescription(studyGroup.getDescription());
        response.setCourseCode(studyGroup.getCourseCode());
        response.setMaxMembers(studyGroup.getMaxMembers());
        response.setCurrentMembers(studyGroup.getMembers().size());
        response.setOwnerName(studyGroup.getOwner().getName());
        return response;
    }
    @Transactional
    public  String leaveStudyGroup(Integer studyGroupId, String email) {
        StudyGroup group = studyGroupRepository
                .findById(studyGroupId)
                .orElseThrow(() ->
                        new RuntimeException("Group not found"));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        boolean isMember = group.getMembers()
                .stream()
                .anyMatch(member ->
                        member.getId().equals(user.getId()));
        if(!isMember){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not member of this study group");
        }

        if (group.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "Owner cannot leave the group"
            );
        }
        group.getMembers().remove(user);
        studyGroupRepository.save(group);
        return "Successfully left the study group!";
    }
    public CreateGroupResponse createStudyGroup(CreateGroupRequest createGroupRequest,String email) {
        //creator of the group is the owner and the first member of the group
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found"));
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
