/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.dao;

import com.iesvdc.acceso.pojo.AluAsi;
import com.iesvdc.acceso.pojo.Alumno;
import com.iesvdc.acceso.pojo.Asignatura;
import com.iesvdc.acceso.pojo.ProAsi;
import com.iesvdc.acceso.pojo.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juangu <jgutierrez at iesvirgendelcarmen.coms>
 */
public class AsignaturaDAOImpl implements AsignaturaDAO {

    Conexion conex;

    private Connection obtenerConexion() throws DAOException {
        if (conex == null) {
            conex = new Conexion();
        }
        return conex.getConexion();
    }

    public AsignaturaDAOImpl() {
    }

    @Override
    public void create(Asignatura as) throws DAOException {
        try {
            if (as.getCiclo().length() > 3 || as.getCurso() > 1 || as.getNombre().length() > 3) {
                Connection con = obtenerConexion();
                PreparedStatement pstm = con.prepareStatement("INSERT INTO ASIGNATURA VALUES(NULL, ?,?,?)");
                pstm.setString(1, as.getNombre());
                pstm.setInt(2, as.getCurso());
                pstm.setString(3, as.getCiclo());
                pstm.execute();
                con.close();
            } else {
                throw new DAOException("Asignatura:Crear: El nombre es demasiado corto");
            }
        } catch (SQLException ex) {
            throw new DAOException("Asignatura:Crear: No puedo conectar a la BBDD");
        }
    }

    @Override
    public void update() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> findAll() throws DAOException {
        Asignatura as;
        List<Asignatura> list_as = new ArrayList<>();
        try {
            Connection con = obtenerConexion();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM ASIGNATURA");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                as = new Asignatura(rs.getString("nombre"),
                        rs.getInt("id"), rs.getInt("curso"), rs.getString("ciclo"));
                list_as.add(as);
            }
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Asignatura:findAll(): No puedo conectar a la BBDD ");
        }
        return list_as;
    }

    @Override
    public List<Asignatura> findByName(String name) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Asignatura findById(Integer id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> findByCurso(Integer curso) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Asignatura> findByCiclo(String ciclo) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alumno> findAlumnos(Integer id_as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alumno> findAlumnos(Asignatura as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profesor> findProfesores(Integer id_as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profesor> findProfesores(Asignatura as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AluAsi> findAluAsi(Integer id_as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AluAsi> findAluAsi(Asignatura as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProAsi> findProAsi(Integer id_as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProAsi> findProAsi(Asignatura as) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
