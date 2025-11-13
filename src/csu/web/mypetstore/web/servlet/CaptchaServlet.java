package csu.web.mypetstore.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CaptchaServlet extends HttpServlet {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 设置背景
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 随机生成验证码
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = chars.charAt(r.nextInt(chars.length()));
            sb.append(c);
        }
        String code = sb.toString();

        // 将验证码保存到 Session
        HttpSession session = req.getSession();
        session.setAttribute("captcha", code);

        // 绘制验证码字符
        g.setFont(new Font("Arial", Font.BOLD, 22));
        for (int i = 0; i < 4; i++) {
            g.setColor(new Color(r.nextInt(200), r.nextInt(100), r.nextInt(200)));
            g.drawString(String.valueOf(code.charAt(i)), 20 * i + 10, 25);
        }

        // 加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            g.drawLine(r.nextInt(WIDTH), r.nextInt(HEIGHT), r.nextInt(WIDTH), r.nextInt(HEIGHT));
        }

        g.dispose();

        // 输出到浏览器
        resp.setContentType("image/jpeg");
        ImageIO.write(image, "jpeg", resp.getOutputStream());
    }
}
