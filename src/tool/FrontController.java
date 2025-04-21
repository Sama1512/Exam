package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
        	// パスを取得
            String path = req.getServletPath().substring(1);

            // ファイル名を取得しクラス名に変換
            String className = path.replace(".a", "A").replace("/", ".");

            // パッケージを固定で補う
            String simpleName = className.substring(className.lastIndexOf('.') + 1);

            // 特定のアクションだけ scoremanager、それ以外は scoremanager.main に振り分け
            if (simpleName.equals("LoginAction") || simpleName.equals("LoginExecuteAction")) {
                className = "scoremanager." + simpleName;
            } else {
                className = "scoremanager.main." + simpleName;
            }

            System.out.println("★ servlet path → " + req.getServletPath());
            System.out.println("★ class name → " + className);

            // アクションクラスのインスタンスを返却
            Action action = (Action) Class.forName(className).getDeclaredConstructor().newInstance();
            // 遷移先URLを取得して処理を実行
            String url = action.execute(req, res);
            req.getRequestDispatcher(url).forward(req, res);

            // ※もしアクションクラスが戻り値でJSPを返す設計なら以下を有効に
            // String url = action.execute(req, res);
            // req.getRequestDispatcher(url).forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページへリダイレクト
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}