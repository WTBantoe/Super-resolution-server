package com.sr.entity.dto;

import com.sr.common.StringUtil;
import com.sr.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author cyh
 * @Date 2021/11/16 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;

    private Long uid;

    private String message;

    private List<String> material;

    public static CommentDTO convertCommentToCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.id = comment.getId();
        commentDTO.uid = comment.getUid();
        commentDTO.message = comment.getMessage();
        commentDTO.material = StringUtil.splitCommentMaterial(comment.getMaterial());
        return commentDTO;
    }

    public static List<CommentDTO> convertCommentListToCommentDTOList(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOS.add(convertCommentToCommentDTO(comment));
        }
        return commentDTOS;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", uid=" + uid +
                ", message='" + message + '\'' +
                ", material=" + material +
                '}';
    }
}
