/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.ClienteDao;

/**
 *
 * @author jonat
 */
public class ClienteTest {
    public static void main(String[] arg) {
        ClienteDao dao = new ClienteDao();
        System.out.println(dao.insertCliente("pepe", "fernandez", "654321"));
        
    }
}
