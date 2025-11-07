package csu.web.mypetstore.service;

import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;
import csu.web.mypetstore.domain.Item;
import csu.web.mypetstore.persistence.CartDao;
import csu.web.mypetstore.persistence.impl.CartDaoImpl;

import java.util.List;

public class CartService {
    private CartDao cartDao;
    private CatalogService catalogService;

    public CartService(CartDao cartDao) {
        this.cartDao = new CartDaoImpl();
    }

    // 根据用户ID查询该用户购物车中所有商品
    public List<CartItem> getCartItemsByUserId(String userId){
        return cartDao.getCartItemsByUserId(userId);
    };

    // 根据用户ID和商品ID查询购物车中是否已有该商品
    public CartItem getCartItem(String userId, String itemId){
        return cartDao.getCartItem(userId, itemId);
    };

    // 向购物车中添加商品
    public void addCartItem(Cart cart, String workingItemId,String userId){

        catalogService = new CatalogService();
        Item item = catalogService.getItem(workingItemId);
        // 1. 内存 Cart 添加商品
        CartItem cartItem = cart.addItem(item, catalogService.isItemInStock(item.getItemId()));

        // 2. 数据库同步
        cartDao.addCartItem(cartItem,userId);
    };

    // 更新购物车中某商品的数量
    public void updateQuantity(Cart cart,String userId, String itemId, int quantity){
        cart.setQuantityByItemId(itemId, quantity);

        cartDao.updateQuantity(userId, itemId, quantity);
    };

    // 删除购物车中某个商品
    public void removeCartItem(Cart cart,String userId, String workingItemId){
        cart.removeItemById(workingItemId);
        cartDao.removeCartItem(userId, workingItemId);
    };

    // 清空用户购物车
    public void clearCart(String userId){
        cartDao.clearCart(userId);
    };
}
