package com.example.exa_3er_ramos_dti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText etNumeroAL, etNombreAL, etPuestoAL, etDiasAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumeroAL = findViewById(R.id.etNumeroCR);
        etNombreAL = findViewById(R.id.etNombreCR);
        etPuestoAL = findViewById(R.id.etPuestoCR);
        etDiasAL = findViewById(R.id.etDiasCR);

    }

    public void altaEmpleado(View view){
        //par abrir programa BOSqlite y generar la base de datos llamada administracion
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //para que la BD sea reescribible (CRUD)
        //Guardar los valores en la variable del formulario
        String Numero = etNumeroAL.getText().toString();
        String Nombre = etNombreAL.getText().toString();
        String Puesto = etPuestoAL.getText().toString();
        String Dias = etDiasAL.getText().toString();

        //Para guardar datos en la tabla articulo utilizando un contenedor en valores de varibales
        ContentValues registro = new ContentValues();
        registro.put("num",Numero);
        registro.put("nom",Nombre);
        registro.put("pues",Puesto);
        registro.put("dias",Dias);

        bd.insert("empleado",null,registro);
        etNumeroAL.setText(null);
        etNombreAL.setText(null);
        etPuestoAL.setText(null);
        etDiasAL.setText(null);

        //confirmar que se dio de alta
        Toast.makeText(this,"Exito al ingresar los datos",Toast.LENGTH_LONG).show();
    }

    public void eliminarProducto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se asigna variable para busqueda por campo distitivo caodigo producto
        String codigoBaja = etNumeroAL.getText().toString();
        //Se genera instrtuccion SQL para que se elimine el registro de producto
        int c = bd.delete("empleado","num="+codigoBaja,null);
        if(c==1){
            Toast.makeText(this,"Registro eliminado de BD exitoso\nVerifica Consulta",Toast.LENGTH_LONG).show();
            //Limpia cajas de texto
            this.etNumeroAL.setText("");
            this.etNombreAL.setText("");
            this.etPuestoAL.setText("");
            this.etDiasAL.setText("");
        }else{
            Toast.makeText(this,"Error\nNo existe Articulo con ese codigo",Toast.LENGTH_LONG).show();
        }

    }//termina metodo para eliminar producto
    //Método para modificar
    public void modificarProductos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se declaran variables que vienen desde formulario sus datos
        String Numero = etNumeroAL.getText().toString();
        String Nombre = etNombreAL.getText().toString();
        String Puesto = etPuestoAL.getText().toString();
        String Dias = etDiasAL.getText().toString();
        //se genera un contenedor para almacenar los valores anteriores
        ContentValues registro=new ContentValues();
        registro.put("num",Numero);
        registro.put("nom",Nombre);
        registro.put("pues",Puesto);
        registro.put("dias",Dias);
        //Se crea la variable que contine la instruccion SQL encargada de modificar y almacenar valor 1 si edito
        int cant = bd.update("empleado",registro,"num="+Numero,null);
        bd.close();
        if(cant==1) {//condicion si realizo modificacion
            Toast.makeText(this,"Registro actualizado de forma correcta",Toast.LENGTH_LONG).show();
            //Se limpian los campos de texto
            etNumeroAL.setText(null);
            etNombreAL.setText(null);
            etPuestoAL.setText(null);
            etDiasAL.setText(null);


        }else {//contrario a no modificacion
            Toast.makeText(this,"Error\nNo se modifico registro",Toast.LENGTH_LONG).show();
        }
    } //termina metodo

    public void consultaProducto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //se asigna una variable para busqueda y consulta por campo distintivo
        String codigoConsulta = etNumeroAL.getText().toString();
        //Cursor recorre los campos de una tabla hasta encontralo por campo distintivo
        Cursor fila = bd.rawQuery("SELECT nom, pues, dias FROM empleado WHERE num="+codigoConsulta,null);

        if(fila.moveToFirst()){//si condicion es verdadera, es decir, encontro un campo y sus datos
            etNombreAL.setText(fila.getString(0));
            etPuestoAL.setText(fila.getString(1));
            etDiasAL.setText(fila.getString(2));
            Toast.makeText(this,"Registro encontrado de forma EXITOSA",Toast.LENGTH_LONG).show();
        }else{//condicion falsa si no encontro un registro
            Toast.makeText(this,"No existe Articulo con ese Codigo\nVerifica",Toast.LENGTH_LONG).show();
            bd.close();
        }

    }//termina metodo consulta producto


}