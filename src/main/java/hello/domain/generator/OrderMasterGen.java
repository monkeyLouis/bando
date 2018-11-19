package hello.domain.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class OrderMasterGen extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		
		String prefix = "OM";
		Connection con = session.connection();
		
		try{
			PreparedStatement ps = con.prepareStatement("SELECT OM_SEQ.nextval from dual");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("NEXTVAL");
				String code = prefix + String.format("%04d",id);
				return code;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
