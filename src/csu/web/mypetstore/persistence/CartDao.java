package csu.web.mypetstore.persistence;

import csu.web.mypetstore.domain.Cart;
import csu.web.mypetstore.domain.CartItem;

import java.util.List;

public interface CartDao {
    // 根据用户ID查询该用户购物车中所有商品
    List<CartItem> getCartItemsByUserId(String userId);

    // 根据用户ID和商品ID查询购物车中是否已有该商品
    CartItem getCartItem(String userId, String itemId);

    // 向购物车中添加新商品
    void addCartItem(CartItem cartItem,String userId);

    // 更新购物车中某商品的数量
    void updateQuantity(String userId, String itemId, int quantity);

    // 删除购物车中某个商品
    void removeCartItem(String userId, String itemId);

    // 清空用户购物车
    void clearCart(String userId);
}
