/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.dao;

//import com.iesvdc.acceso.pojo.Alumno;
import com.iesvdc.acceso.pojo.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author profesor
 */
public class ProfesorDAOImpl implements ProfesorDAO {

    public ProfesorDAOImpl() {
    }

    Conexion conex;

    private Connection obtenerConexion() throws DAOException {
        if (conex == null) {
            conex = new Conexion();
        }
        return conex.getConexion();
    }

    @Override
    public void create(Profesor pro) throws DAOException {
        try {
            if (pro.getApellido().length() >= 3 && pro.getNombre().length() > 1) {
                Connection con = obtenerConexion();
                PreparedStatement pstm = con.prepareStatement("INSERT INTO PROFESOR VALUES(NULL, ?,?)");
                pstm.setString(1, pro.getNombre());
                pstm.setString(2, pro.getApellido());
                pstm.execute();
                con.close();
            } else {
                throw new DAOException("Profesor:Crear: El nombre es demasiado corto");
            }
        } catch (SQLException ex) {
            throw new DAOException("Profesor:Crear: No puedo conectar a la BBDD");
        }
    }

    @Override
    public void update(Profesor old_pro, Profesor new_pro) throws DAOException {
        update(old_pro.getId(), new_pro);
    }

    @Override
    public void update(Integer old_id, Profesor new_pro) throws DAOException {
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement(" UPDATE PROFESOR SET id=?, nombre=?, apellido=? WHERE id=?");
            pstm.setInt(4, old_id);
            pstm.setInt(1, new_pro.getId());
            pstm.setString(2, new_pro.getNombre());
            pstm.setString(3, new_pro.getApellido());
            pstm.execute();
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Profesor:Update: No puedo conectar a la BBDD");
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement("DELETE FROM PROFESOR WHERE id=?");
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Profesor:Delete: No puedo conectar a la BBDD");
        }
    }

    @Override
    public void delete(Profesor pro) throws DAOException {
        delete(pro.getId());
    }

    @Override
    public Profesor findById(Integer Id) throws DAOException {
        Profesor pro;
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM PROFESOR WHERE id=?");
            pstm.setInt(1, Id);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            pro = new Profesor(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
            con.close();
        } catch (SQLException ex) {
            pro = new Profesor(-1, "error", "error");
            throw new DAOException("Profesor:findById: No puedo conectar a la BBDD ");
        }
        return pro;
    }

    @Override
    public List<Profesor> findByNombre(String nombre) throws DAOException {
        Profesor pro;
        List<Profesor> list_pro = new ArrayList<>();
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM PROFESOR WHERE nombre=?");
            pstm.setString(1, nombre);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                pro = new Profesor(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
                list_pro.add(pro);
            }
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Profesor:findByNombre: No puedo conectar a la BBDD ");
        }
        return list_pro;
    }

    @Override
    public List<Profesor> findByApellido(String apellido) throws DAOException {
        Profesor pro;
        List<Profesor> list_pro = new ArrayList<>();
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM PROFESOR WHERE apellido=?");
            pstm.setString(1, apellido);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                pro = new Profesor(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
                list_pro.add(pro);
            }
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Profesor:findByApellido: No puedo conectar a la BBDD ");
        }
        return list_pro;
    }

    @Override
    public List<Profesor> findByNombreApellido(String nombre, String apellido) throws DAOException {
        Profesor pro;
        List<Profesor> list_pro = new ArrayList<>();
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement(
                    "SELECT * FROM PROFESOR WHERE nombre=? AND apellido=?");
            pstm.setString(1, nombre);
            pstm.setString(2, apellido);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                pro = new Profesor(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
                list_pro.add(pro);
            }
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Profesor:findByApellido: No puedo conectar a la BBDD ");
        }
        return list_pro;
    }

    @Override
    public List<Profesor> findAll() throws DAOException {
        Profesor pro;
        List<Profesor> list_pro = new ArrayList<>();
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM PROFESOR");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                pro = new Profesor(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
                list_pro.add(pro);
            }
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Profesor:findByApellido: No puedo conectar a la BBDD ");
        }
        return list_pro;
    }

}
