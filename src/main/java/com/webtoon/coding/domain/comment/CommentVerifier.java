package com.webtoon.coding.domain.comment;

import com.webtoon.coding.exception.DomainException;
import com.webtoon.coding.exception.MsgType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommentVerifier {

    void verify(Comment comment) {

        if (isNotIncludeSymbol(comment.getComment())) {
            throw new DomainException(MsgType.CommentDataException);
        }

        if (ObjectUtils.isEmpty(comment.getType())) {
            throw new DomainException(MsgType.EvaluationDataException);
        }
    }

    private boolean isNotIncludeSymbol(String comment) {

        if (StringUtils.isEmpty(comment)) {
            return true;
        }

        if (StringUtils.isBlank(comment)) {
            return false;
        }

        Pattern pattern2 = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

        return pattern2.matcher(comment).find();
    }
}
