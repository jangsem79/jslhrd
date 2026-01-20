package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class BlogDao {

	public void blogInsert(BlogDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql ="insert into blog (bno,name,title,content,imgfile) "
				+ " values (blog_seq.nextval,?,?,?,?)";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getImgfile());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//검색
	public List<BlogDto> getAll() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="select * from blog order by bno desc";
		
		//검색된 결과 여러개 리턴 하려면 dto저장하는 가변 배열 만든다
		List<BlogDto> list = new ArrayList<BlogDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BlogDto dto = new BlogDto();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<BlogDto> getSearchAndPaging(String keyword, int page, int pageSize) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="select * from (\r\n"
				+ "    select rownum rn, aaa.* from\r\n"
				+ "        (select * from blog where title like ? or content like ? order by bno desc) aaa "
				+ "        where rownum <= ?)  "
				+ "    where rn > ?";
		
		//검색된 결과 여러개 리턴 하려면 dto저장하는 가변 배열 만든다
		List<BlogDto> list = new ArrayList<BlogDto>();
		
		int offset = (page-1) * pageSize;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, '%'+keyword+'%');
			pstmt.setString(2, '%'+keyword+'%');
			pstmt.setInt(3, offset+pageSize);
			pstmt.setInt(4, offset);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BlogDto dto = new BlogDto();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//총레코드 개수
	public int getSearchCount(String keyword) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select count(*) as cnt from blog where title like ? or content like ?";
		
		BlogDto dto = new BlogDto();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, '%'+keyword+'%');
			pstmt.setString(2, '%'+keyword+'%');
			rs = pstmt.executeQuery();
			while(rs.next()) {
				return rs.getInt("cnt");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	//view
	public BlogDto getSelectByBno(int bno) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from blog where bno=?";
		
		BlogDto dto = new BlogDto();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	//조회수증가
	public void viewCount(int bno) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update blog set views = views + 1 where bno = ?";
		
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//삭제
	public void blogDelete(int bno) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete blog where bno = ?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void blogUpdate(BlogDto dto) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql=null;
		
		if(dto.getImgfile() !=null && !dto.getImgfile().isEmpty()) {
			sql = "update blog set title=?,content=?,imgfile=? "
					+ " where bno = ?";
		}else {
			sql = "update blog set title=?,content=? where bno = ?";
		}
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			if(dto.getImgfile() != null && !dto.getImgfile().isEmpty()) {
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getImgfile());
				pstmt.setInt(4, dto.getBno());
			}else {
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getContent());
				pstmt.setInt(3, dto.getBno());
			}
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	//index
	public List<BlogDto> getIndex() {
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="select * from  blog where rownum < 4 order by bno desc";
		
		//검색된 결과 여러개 리턴 하려면 dto저장하는 가변 배열 만든다
		List<BlogDto> list = new ArrayList<BlogDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BlogDto dto = new BlogDto();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	//찜하기
	public int myWishInsert(String userid, int blogbno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql ="insert into mywish (wish_bno,blog_bno,userid) "
				+ " values (mywish_seq.nextval,?,?)";
		
		int result = 0;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,blogbno);
			pstmt.setString(2, userid);
			result = pstmt.executeUpdate();
		}catch(SQLIntegrityConstraintViolationException e) {
			//DB의 무결성 제약조건을 위반 했을 때 발생되는 예외
			e.printStackTrace();
			//unique 제약조건 위반 시 예외처리만 출력하는 것이지 에러가 아니다
			result = -1;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//찜 목록
	public List<Wishdto> myWishList(String userid) {
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="select w.wish_bno, b.bno,b.title,b.content,b.imgfile,b.views,b.regdate\r\n"
				+ "from blog b \r\n"
				+ "join mywish w \r\n"
				+ "on b.bno = w.blog_bno  and w.userid=? \r\n"
				+ "order by w.wish_bno desc";
		
		//검색된 결과 여러개 리턴 하려면 dto저장하는 가변 배열 만든다
		List<Wishdto> list = new ArrayList<Wishdto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Wishdto dto = new Wishdto();
				dto.setWish_bno(rs.getInt("wish_bno"));
				dto.setBno(rs.getInt("bno"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	//댓글등록
	public ReplyDto replyInsert(ReplyDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql ="insert into reply (bno,blog_bno,userid,replytext) "
				+ " values (reply_seq.nextval,?,?,?)";
		
		ReplyDto resultDto = null;
		String seqsql = null;
		
		try {
			conn = DBManager.getInstance();
			// 1) insert
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBlog_bno());
			pstmt.setString(2, dto.getUserid());
			pstmt.setString(3, dto.getReplytext());
			pstmt.executeUpdate();
			pstmt.close();
			
			// 2) get the sequence value from this session
			seqsql = "select reply_seq.currval from dual";
			pstmt = conn.prepareStatement(seqsql);
			rs = pstmt.executeQuery();
			int newBno = -1;
			if(rs.next()) {
				newBno = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
			// 3) select the inserted row by bno
			String selectSql = "select * from reply where bno = ?";
			pstmt = conn.prepareStatement(selectSql);
			pstmt.setInt(1, newBno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				resultDto = new ReplyDto();
				resultDto.setBno(rs.getInt("bno"));
				resultDto.setBlog_bno(rs.getInt("blog_bno"));
				resultDto.setUserid(rs.getString("userid"));
				resultDto.setReplytext(rs.getString("replytext"));
				String reg = rs.getString("regdate");
				if(reg != null && reg.length() >= 10) {
					resultDto.setRegdate(reg.substring(0,10));
				} else {
					resultDto.setRegdate(reg);
				}
				return resultDto;
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return resultDto;
	}
	
	//댓글 리스트
	public List<ReplyDto> seelctReplyByBno(int bno) {
		//bno 블러그 번호
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql="select * from reply where blog_bno = ?";
		
		//검색된 결과 여러개 리턴 하려면 dto저장하는 가변 배열 만든다
		List<ReplyDto> list = new ArrayList<ReplyDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReplyDto dto = new ReplyDto();
				dto.setBno(rs.getInt("bno"));
				dto.setBlog_bno(rs.getInt("blog_bno"));
				dto.setUserid(rs.getString("userid"));
				dto.setReplytext(rs.getString("replytext"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	//ReplyDelete 클래스 참조 댓글 삭제
	public boolean replyDelete(int bno, String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete from reply where bno = ? and userid = ?";
		
		int result = 0;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, userid);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result > 0;
	}

	// 댓글 수정: 작성자 일치 조건으로 본인 댓글만 수정 가능
	public boolean replyUpdate(int bno, String userid, String replytext) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update reply set replytext = ? where bno = ? and userid = ?";
		int result = 0;
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, replytext);
			pstmt.setInt(2, bno);
			pstmt.setString(3, userid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result > 0;
	}

}