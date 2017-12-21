/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iesvdc.acceso.dao;

import com.iesvdc.acceso.pojo.Usuario;
import java.util.List;

/**
 *
 * @author Juangu <jgutierrez at iesvirgendelcarmen.coms>
 */
public interface UsuarioDAO {
    public void create(Usuario us) throws DAOException;
    public void update(Usuario old_us, Usuario new_us) throws DAOException;
    public void update(Integer old_id, Usuario new_us) throws DAOException;
    public void delete(Integer id) throws DAOException;
    public void delete(Usuario us) throws DAOException;
    public Usuario findById(Integer Id) throws DAOException;
    public List<Usuario> findByUsername(String nombre) throws DAOException;
    public List<Usuario> findByPassword(String apellido) throws DAOException;
    public List<Usuario> findAll() throws DAOException;
    public List<Usuario> findByUsernamePassword(String nombre, String apellido) throws DAOException;
}
