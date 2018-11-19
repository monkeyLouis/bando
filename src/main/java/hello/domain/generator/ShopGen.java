package hello.domain.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class ShopGen extends SequenceStyleGenerator {
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
		
		String prefix = "S";
		Connection con = session.connection();
		
		try{
			PreparedStatement pstmt = con.prepareStatement("SELECT SHOP_SEQ.nextval from dual");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("NEXTVAL");
				String code = prefix.concat(String.format("%04d", id));
				return code;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
