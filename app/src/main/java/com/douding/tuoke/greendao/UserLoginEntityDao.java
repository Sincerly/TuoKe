package com.douding.tuoke.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.douding.tuoke.bean.entity.UserLoginEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_LOGIN_ENTITY".
*/
public class UserLoginEntityDao extends AbstractDao<UserLoginEntity, Long> {

    public static final String TABLENAME = "USER_LOGIN_ENTITY";

    /**
     * Properties of entity UserLoginEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Pwd = new Property(2, String.class, "pwd", false, "PWD");
        public final static Property UserId = new Property(3, String.class, "userId", false, "USER_ID");
    }


    public UserLoginEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UserLoginEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_LOGIN_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"PWD\" TEXT," + // 2: pwd
                "\"USER_ID\" TEXT);"); // 3: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_LOGIN_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserLoginEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String pwd = entity.getPwd();
        if (pwd != null) {
            stmt.bindString(3, pwd);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(4, userId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserLoginEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String pwd = entity.getPwd();
        if (pwd != null) {
            stmt.bindString(3, pwd);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(4, userId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserLoginEntity readEntity(Cursor cursor, int offset) {
        UserLoginEntity entity = new UserLoginEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pwd
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // userId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserLoginEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPwd(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserLoginEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserLoginEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserLoginEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
