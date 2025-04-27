package umc.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 479029197L;

    public static final QUser user = new QUser("user");

    public final umc.study.domain.common.QBaseEntity _super = new umc.study.domain.common.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final DatePath<java.time.LocalDate> birthdate = createDate("birthdate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<umc.study.domain.enums.Gender> gender = createEnum("gender", umc.study.domain.enums.Gender.class);

    public final ListPath<umc.study.domain.mapping.MemberAgree, umc.study.domain.mapping.QMemberAgree> memberAgreeList = this.<umc.study.domain.mapping.MemberAgree, umc.study.domain.mapping.QMemberAgree>createList("memberAgreeList", umc.study.domain.mapping.MemberAgree.class, umc.study.domain.mapping.QMemberAgree.class, PathInits.DIRECT2);

    public final ListPath<umc.study.domain.mapping.MemberMission, umc.study.domain.mapping.QMemberMission> memberMissionList = this.<umc.study.domain.mapping.MemberMission, umc.study.domain.mapping.QMemberMission>createList("memberMissionList", umc.study.domain.mapping.MemberMission.class, umc.study.domain.mapping.QMemberMission.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final BooleanPath notificationStatus = createBoolean("notificationStatus");

    public final StringPath phonenumber = createString("phonenumber");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final ListPath<umc.study.domain.mapping.Review, umc.study.domain.mapping.QReview> reviewList = this.<umc.study.domain.mapping.Review, umc.study.domain.mapping.QReview>createList("reviewList", umc.study.domain.mapping.Review.class, umc.study.domain.mapping.QReview.class, PathInits.DIRECT2);

    public final EnumPath<umc.study.domain.enums.SocialType> socialType = createEnum("socialType", umc.study.domain.enums.SocialType.class);

    public final StringPath specAddress = createString("specAddress");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<umc.study.domain.mapping.UserFavorCategory, umc.study.domain.mapping.QUserFavorCategory> userFavorCategoryList = this.<umc.study.domain.mapping.UserFavorCategory, umc.study.domain.mapping.QUserFavorCategory>createList("userFavorCategoryList", umc.study.domain.mapping.UserFavorCategory.class, umc.study.domain.mapping.QUserFavorCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final ListPath<UserNotification, QUserNotification> userNotificationList = this.<UserNotification, QUserNotification>createList("userNotificationList", UserNotification.class, QUserNotification.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

