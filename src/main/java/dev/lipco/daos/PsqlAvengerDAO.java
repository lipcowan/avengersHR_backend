package dev.lipco.daos;

import dev.lipco.entities.Avenger;
import dev.lipco.utils.ConnectionUtil;

import java.sql.*;
import java.util.Set;
import java.util.HashSet;

public class PsqlAvengerDAO implements AvengerDAO{

    @Override
    public Set<Avenger> getAllMembers() {
        Set<Avenger> members = new HashSet<Avenger>();
        try (Connection conn = ConnectionUtil.makeConnection()){
            String sqlGetMembers = "select * from avengers";
            PreparedStatement ps = conn.prepareStatement(sqlGetMembers);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Avenger a = new Avenger();
                a.setId(rs.getInt("id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setManager(rs.getBoolean("is_manager"));
                members.add(a);
            }
            return members;
        } catch (SQLException e) {
            e.printStackTrace();
            return members;
        }
    }

    @Override
    public Avenger getMemberById(int id) {
        try(Connection conn = ConnectionUtil.makeConnection()){
            String sqlGetMember = "select * from avengers where id = ?";
            PreparedStatement ps = conn.prepareStatement(sqlGetMember);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Avenger a = new Avenger();
            a.setId(rs.getInt("id"));
            a.setFirstName(rs.getString("first_name"));
            a.setLastName(rs.getString("last_name"));
            a.setManager(rs.getBoolean("is_manager"));
            return a;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
