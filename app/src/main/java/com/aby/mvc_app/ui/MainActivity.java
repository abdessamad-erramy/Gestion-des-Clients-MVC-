// app/src/main/java/com/example/mvc_app/ui/MainActivity.java
package com.aby.mvc_app.ui;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.aby.mvc_app.R;
import com.aby.mvc_app.data.ClientDbHelper;
import com.aby.mvc_app.model.Client;

import java.util.*;

/**
 * Controller (MVC) :
 * - Récupère les actions utilisateur (clic bouton, clic long)
 * - Appelle le Model (ClientDbHelper) pour exécuter les CRUD
 * - Met à jour la View (ListView) avec un ArrayAdapter
 */
public class MainActivity extends AppCompatActivity {

    // Références UI (View)
    private EditText etNom, etVille;

    private long selectedId = -1;
    private Button btnAdd , btnUp;
    private ListView lv;

    // Accès au Model (BD locale)
    private ClientDbHelper db;

    // Source d'affichage pour la liste (affiche Client.toString())
    private ArrayAdapter<Client> adapter;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main); // charge la View (XML)

        // 1) Lier la View (findViewById)
        etNom  = findViewById(R.id.etNom);
        etVille = findViewById(R.id.etVille);
        btnAdd = findViewById(R.id.btnAdd);
        lv     = findViewById(R.id.lvClients);
        btnUp = findViewById(R.id.btnUp);

        // 2) Instancier le Model (accès BD)
        db = new ClientDbHelper(this);

        // 3) Préparer l'adapter pour la ListView (Vue)
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );
        lv.setAdapter(adapter);

        // 4) Charger et afficher les données au démarrage (Model → View)
        render(db.getAllClients());

        // 5) Contrôleur : Ajouter un client (événement UI → Model → refresh View)
        btnAdd.setOnClickListener(v -> {
            String nom   = etNom.getText().toString().trim();
            String ville = etVille.getText().toString().trim();

            if (nom.isEmpty() || ville.isEmpty()) {
                Toast.makeText(this, "Nom et ville obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = db.insertClient(nom, ville);
            if (id != -1) {
                etNom.setText("");
                etVille.setText("");
                render(db.getAllClients()); // recharger la liste depuis le Model
            } else {
                Toast.makeText(this, "Insertion échouée", Toast.LENGTH_SHORT).show();
            }
        });

        // 6) Contrôleur : SUPPRESSION par clic long (UI → Model → refresh View)
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            Client c = adapter.getItem(position);
            if (c == null) {
                Toast.makeText(this, "Élément introuvable", Toast.LENGTH_SHORT).show();
                return true;
            }
            boolean ok = db.deleteClient(c.id);
            if (ok) {
                Toast.makeText(this, "Client supprimé", Toast.LENGTH_SHORT).show();
                render(db.getAllClients()); // refresh View
            } else {
                Toast.makeText(this, "Suppression échouée", Toast.LENGTH_SHORT).show();
            }
            return true; // consomme l'événement
        });

        lv.setOnItemClickListener( (parent, view, position, id) -> {
            Client c = adapter.getItem(position);
            if (c == null) {
                Toast.makeText(this, "Élément introuvable", Toast.LENGTH_SHORT).show();
                return;

            }
            etNom.setText(c.nom);
            etVille.setText(c.ville);
            selectedId = c.id;






        });
        btnUp.setOnClickListener(v -> {

            String nom = etNom.getText().toString().trim();
            String ville = etVille.getText().toString().trim();

            if (selectedId == -1) {
                Toast.makeText(this, "Choisir un client d'abord", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nom.isEmpty() || ville.isEmpty()) {
                Toast.makeText(this, "Nom et ville obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = db.updateClient(selectedId, nom, ville);

            if (ok) {
                Toast.makeText(this, "Client modifié", Toast.LENGTH_SHORT).show();

                etNom.setText("");
                etVille.setText("");
                selectedId = -1;

                render(db.getAllClients()); // refresh list
            } else {
                Toast.makeText(this, "Modification échouée", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /** Met à jour la View (ListView) avec les données du Model */
    private void render(List<Client> data) {
        adapter.clear();
        if (data != null) adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }
}