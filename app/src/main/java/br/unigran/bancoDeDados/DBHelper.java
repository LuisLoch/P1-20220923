package br.unigran.bancoDeDados;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) { super(context, "BancoConsumos", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE abastecimentos(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "kmAtual INTEGER," +
            "qtdAbastecida INTEGER," +
            "valor FLOAT," +
            "data DATE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
