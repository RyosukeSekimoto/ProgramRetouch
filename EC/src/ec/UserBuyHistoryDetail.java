package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//セッション
		HttpSession session = request.getSession();

		try {
			//リクエストパラメータを取得
			request.setCharacterEncoding("UTF-8");
			int buyId = Integer.parseInt(request.getParameter("buy_id"));

			//リクエストスコープに購入情報を保存
			BuyDataBeans bdb = BuyDAO.getBuyDataBeansByBuyId(buyId);
			request.setAttribute("bdb", bdb);

			//リクエストスコープに購入情報に紐付いたアイテムリストを保存
			ArrayList<ItemDataBeans> buyDetailItemList = BuyDetailDAO.getItemDataBeansListByBuyId(buyId);
			request.setAttribute("buyDetailItemList", buyDetailItemList);

			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("errorMessage", e.toString());
				response.sendRedirect("Error");
			}

	}
}
