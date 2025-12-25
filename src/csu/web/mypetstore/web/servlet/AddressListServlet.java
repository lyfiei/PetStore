package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Address;
import csu.web.mypetstore.service.AddressService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AddressListServlet extends HttpServlet {
    private AddressService addressService = new AddressService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account account = (Account) request.getSession().getAttribute("loginAccount");
        if (account == null) {
            response.sendRedirect("signonForm");
            return;
        }

        List<Address> addressList = addressService.getAddressListByUsername(account.getUsername());
        request.setAttribute("addressList", addressList);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/account/addressList.jsp");
        rd.forward(request, response);
    }
}
