package com.byted.camp.todolist.db;

import android.provider.BaseColumns;

import com.byted.camp.todolist.operation.db.FeedReaderContract;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量
    public static class TodoTable implements BaseColumns {
        public static final String TABLE_NAME = "todoTable";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_PRIORITY = "priority";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoTable.TABLE_NAME + " (" +
                    TodoTable._ID + " INTEGER PRIMARY KEY," +
                    TodoTable.COLUMN_CONTENT + " TEXT," +
                    TodoTable.COLUMN_DATE + " DATETIME," +
                    TodoTable.COLUMN_STATE + " INTEGER," +
                    TodoTable.COLUMN_PRIORITY + " INTEGER)";

    public static final String SQL_ADD_PRIORITY =
            "ALTER TABLE " + TodoTable.TABLE_NAME +
                    " ADD " + TodoTable.COLUMN_PRIORITY + " INTEGER";

    private TodoContract() {
    }

}
