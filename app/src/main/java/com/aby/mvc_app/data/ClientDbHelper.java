package com.aby.mvc_app.data;



// app/src/main/java/com/example/mvc_app/data/ClientDbHelper.java

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

import com.aby.mvc_app.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère la base SQLite (couche Model/Persistance).
 * - Crée la table
 * - Fournit des méthodes CRUD minimales
 * MVC : aucune logique d'interface ici.
 */
public class ClientDbHelper extends SQLiteOpenHelper {

    // Nom/version BD (voir rappels SQLiteOpenHelper dans tes slides)
    private static final String DB_NAME = "clients.db";   // fichier .db local
    private static final int DB_VERSION = 1;              // incrémentez pour migrations

    // Schéma de la table
    public static final String TBL = "client";
    public static final String COL_ID = "idClt";
    public static final String COL_NOM = "nomClt";
    public static final String COL_VILLE = "villeClt";

    public ClientDbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    // Créée au premier lancement (structure physique de la BD)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TBL + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOM + " TEXT, " +
                COL_VILLE + " TEXT)";
        db.execSQL(sql);

        // (Optionnel) Jeux d'essai pour démarrer
        ContentValues cv = new ContentValues();
        cv.put(COL_NOM, "Sara"); cv.put(COL_VILLE, "Casablanca"); db.insert(TBL, null, cv);
        cv.clear();
        cv.put(COL_NOM, "Yassine"); cv.put(COL_VILLE, "Rabat"); db.insert(TBL, null, cv);
    }

    // Appelée si DB_VERSION augmente (migration simple ici)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL);
        onCreate(db);
    }

    /** INSERT : ajoute un client, retourne l'id inséré (ou -1 si échec) */
    public long insertClient(String nom, String ville) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOM, nom);
        cv.put(COL_VILLE, ville);
        return db.insert(TBL, null, cv);
    }

    /** DELETE : supprime un client par id, retourne true si >0 lignes supprimées */
    public boolean deleteClient(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TBL, COL_ID + "=?", new String[]{ String.valueOf(id) });
        return rows > 0;
    }
    /** UPDATE : modifie un client par id */
    public boolean updateClient(long id, String nom, String ville) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NOM, nom);
        cv.put(COL_VILLE, ville);

        int rows = db.update(
                TBL,
                cv,
                COL_ID + "=?",
                new String[]{ String.valueOf(id) }
        );

        return rows > 0;
    }

    /** SELECT * : lit tous les clients (ordre décroissant sur id) */
    public List<Client> getAllClients() {
        List<Client> out = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " + COL_ID + "," + COL_NOM + "," + COL_VILLE +
                        " FROM " + TBL + " ORDER BY " + COL_ID + " DESC",
                null
        );
        try {
            while (c.moveToNext()) {
                long id = c.getLong(0);
                String nom = c.getString(1);
                String ville = c.getString(2);
                out.add(new Client(id, nom, ville));
            }
        } finally {
            c.close();
        }
        return out;
    }
}