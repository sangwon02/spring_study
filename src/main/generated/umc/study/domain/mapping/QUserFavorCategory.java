package umc.study.domain.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFavorCategory is a Querydsl query type for UserFavorCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFavorCategory extends EntityPathBase<UserFavorCategory> {

    private static final long serialVersionUID = 709493615L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFavorCategory userFavorCategory = new QUserFavorCategory("userFavorCategory");

    public final umc.study.domain.common.QBaseEntity _super = new umc.study.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final umc.study.domain.QFoodCategory foodCategory;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final umc.study.domain.QUser user;

    public final NumberPath<Long> userFavorId = createNumber("userFavorId", Long.class);

    public QUserFavorCategory(String variable) {
        this(UserFavorCategory.class, forVariable(variable), INITS);
    }

    public QUserFavorCategory(Path<? extends UserFavorCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFavorCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFavorCategory(PathMetadata metadata, PathInits inits) {
        this(UserFavorCategory.class, metadata, inits);
    }

    public QUserFavorCategory(Class<? extends UserFavorCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodCategory = inits.isInitialized("foodCategory") ? new umc.study.domain.QFoodCategory(forProperty("foodCategory")) : null;
        this.user = inits.isInitialized("user") ? new umc.study.domain.QUser(forProperty("user")) : null;
    }

}

