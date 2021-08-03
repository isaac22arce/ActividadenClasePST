package com.example.ta5_grupo1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextMatricula, editTextNombres, editTextApellidos, editTextCodMateria;
    private Button btnRegistrar, btnLista;
    private TextView tvMatricula, tvNombre, tvApellido, tvCodigo;
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMatricula = findViewById(R.id.editTextMatricula);
        editTextNombres = findViewById(R.id.editTextNombres);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextCodMateria = findViewById(R.id.editTextCodMateria);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnLista = findViewById(R.id.btnLista);
        tvMatricula = findViewById(R.id.tvMatricula);
        tvNombre = findViewById(R.id.tvNombre);
        tvApellido = findViewById(R.id.tvApellido);
        tvCodigo = findViewById(R.id.tvCodigo);

        btnRegistrar.setOnClickListener(this);
        btnLista.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistrar:
                db = admin.getWritableDatabase();
                String matriculaText = editTextMatricula.getText().toString();
                String nombresText = editTextNombres.getText().toString();
                String apellidosText = editTextApellidos.getText().toString();
                String codMateriaText = editTextCodMateria.getText().toString();

                if (!matriculaText.isEmpty() && !nombresText.isEmpty() && !apellidosText.isEmpty() && !codMateriaText.isEmpty()) {
                    db.execSQL("insert into consejeros(matricula,nombres,apellidos,codMateria)" +
                            "values(" + matriculaText + ",'" + nombresText + "','" + apellidosText + "','" + codMateriaText + "')");
                    db.close();
                    editTextMatricula.setText("");
                    editTextNombres.setText("");
                    editTextApellidos.setText("");
                    editTextCodMateria.setText("");
                    Toast.makeText(MainActivity.this, "Se cargo la informacion del consejero", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Falta llenar informacion en las casillas", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnLista:
                admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
                db = admin.getWritableDatabase();
                matriculaText = editTextMatricula.getText().toString();
                if (!matriculaText.isEmpty()) {
                    Cursor fila = db.rawQuery(
                            "select matricula,nombres,apellidos,codMateria from consejeros where" + " matricula =" + matriculaText, null);
                    if (fila.moveToFirst()) {
                        tvMatricula.setText("Matricula: " + fila.getString(0));
                        tvNombre.setText("Nombre: " + fila.getString(1));
                        tvApellido.setText("Apellido: " + fila.getString(2));
                        tvCodigo.setText("Código Materia: " + fila.getString(3));
                        Toast.makeText(this, "Consulta realizada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No existe estudiante", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                } else {
                    Toast.makeText(this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}