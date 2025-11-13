package csu.web.mypetstore.web.servlet;

import csu.web.mypetstore.domain.Account;
import csu.web.mypetstore.domain.Address;
import csu.web.mypetstore.service.AddressService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddAddressServlet extends HttpServlet {
    private AddressService addressService = new AddressService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account account = (Account) request.getSession().getAttribute("loginAccount");
        if (account == null) {
            response.sendRedirect("signonForm");
            return;
        }

        Address address = new Address();
        address.setUsername(account.getUsername());
        address.setFirstName(request.getParameter("firstName"));
        address.setLastName(request.getParameter("lastName"));
        address.setAddress1(request.getParameter("address1"));
        address.setAddress2(request.getParameter("address2"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZip(request.getParameter("zip"));
        address.setCountry(request.getParameter("country"));

        addressService.addAddress(address);

        response.sendRedirect("addressList");
    }
}
