package csu.web.mypetstore.persistence.impl;

import csu.web.mypetstore.domain.Sequence;
import csu.web.mypetstore.persistence.DBUtil;
import csu.web.mypetstore.persistence.SequenceDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SequenceDaoImpl implements SequenceDao {

    private static final String GET_SEQUENCE="SELECT name, nextid FROM SEQUENCE WHERE NAME = ?";
    private static final String UPDATE_SEQUENCE="UPDATE SEQUENCE SET NEXTID = ?  WHERE NAME = ?";

    @Override
    public Sequence getSequence(Sequence sequence) {
        Sequence result = null;
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_SEQUENCE)
        ) {
            ps.setString(1, sequence.getName());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = new Sequence();
                result.setName(rs.getString("name"));
                result.setNextId(rs.getInt("nextId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateSequence(Sequence sequence) {
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_SEQUENCE)
        ) {
            ps.setInt(1, sequence.getNextId());
            ps.setString(2, sequence.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
