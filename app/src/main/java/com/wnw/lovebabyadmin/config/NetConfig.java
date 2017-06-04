package com.wnw.lovebabyadmin.config;

/**
 * Created by wnw on 2017/4/9.
 */

public class NetConfig {
    //public final static String SERVICE = "http://119.29.182.235:8080/babyTest/";
    public final static String SERVICE = "http://119.29.40.196:8080/babyTest/";

    public final static String IMAGE_PATH = "http://119.29.40.196:8080/babyTest";


    public final static String IMAGE_UPLOAD = "http://119.29.40.196:8080/babyTest/upload";

    //Admin
    public final static String ADMIN_LOGIN = "adminLogin?";

    //Orders
    public final static String FIND_BE_SENT_ORDERS = "findBeSentOrder?";
    public final static String UPDATE_ORDER_TYPE = "updateOrderType?";

    //Shop
    public final static String FIND_SHOP_BY_TYPE = "findShopByType?";
    public final static String UPDATE_SHOP_TYPE = "updateShopType?";

    //Article
    public final static String INSERT_ARTICLE = "insertArticle?";
    public final static String UPDATE_ARTICLE = "updateArticle?";
    public final static String DELETE_ARTICLE = "deleteArticle?";
    public final static String UPDATE_ARTICLE_READ_TIMES = "updateArticleReadTimes?";
    public final static String UPDATE_ARTICLE_LIKE_TIMES = "updateArticleLikeTimes?";
    public final static String FIND_ALL_ARTICLE = "findAllArticles?";


    //User

    public final static String LOGIN = "login?";
    public final static String REGISTER = "register?";
    public final static String UPDATE_USER_PASSWORD = "updateUserPassword?";
    public final static String FIND_USER_BY_ID = "findUserById?";

    //userInfo
    public final static String FIND_USER_INFO_BY_USER_ID = "findUserInfoByUserId?";
    public final static String UPDATE_USER_INFO = "updateUserInfo?";

    //ShoppingCar updateShoppingCarProductCount
    public final static String DELETE_SHOPPING_CAR = "deleteShoppingCar?";
    public final static String FIND_SHOPPING_CAR_BBY_USER_ID = "findShoppingCarByUserId?";
    public final static String INSERT_SHOPPING_CAR = "insertShoppingCar?";
    public final static String UPDATE_SHOPPING_CAR_PRODUCT_COUNT = "updateShoppingCarProductCount?";

    //Order
    public final static String INSERT_ORDER = "insertOrder?";
    public final static String UPDATE_ORDER = "updateOrder?";
    public final static String FIND_ORDER_BY_ID = "findOrderById?";
    public final static String FIND_ORDER_BY_USER_ID = "findOrderByUserId?";
    public final static String FIND_ORDER_BY_SHOP_ID = "findOrderByShopId?";
    public final static String FIND_ORDER_BY_INVITEE = "findOrderByInvitee?";

    //shop
    public final static String INSERT_SHOP = "insertShop?";
    public final static String UPDATE_SHOP = "updateShop?";
    public final static String FIND_SHOP_BY_ID = "findShopById?";
    public final static String FIND_SHOP_BY_USER_ID = "findShopByUserId?";
    public final static String FIND_SHOP_BY_INVITEE = "findShopByInvitee?";

    //deal
    public final static String INSERT_DEAL = "insertDeal?";
    public final static String UPDATE_DEAL = "updateDeal?";
    public final static String FIND_DEAL_BY_ID = "findDealById?";
    public final static String FIND_DEAL_BY_ORDER_ID = "findDealByOrderId?";
    public final static String FIND_INCOME_BY_SHOP_ID = "findIncomeByShopId?";
    public final static String FIND_INCOME_BY_INVITEE = "findIncomeByInvitee?";
    public final static String FIND_SUM_PRICE = "findSumPrice";
    public final static String FIND_PRODUCT_SALE_COUNT = "findProductSaleCount";


    //user wallet
    public final static String UPDATE_WALLET_PASSWORD = "updateWalletPassword?";
    public final static String SUB_WALLET_MONEY = "subWalletMoney?";
    public final static String VALITE_WALLET = "valiteWallet?";
    public final static String FIND_WALLET_MONEY_BY_USERID = "findWalletMoneyByUserId?";

    //address
    public final static String DELETE_RECE_ADDRESS = "deleteReceAddress?";
    public final static String FIND_RECE_ADDRESS_BY_ID = "findReceAddress?";
    public final static String FIND_RECE_ADDRESS_BY_USER_ID = "findReceAddressByUserId?";
    public final static String INSERT_RECE_ADDRESS = "insertReceAddress?";
    public final static String UPDATE_RECE_ADDRESS = "updateReceAddress?";

    //product
    public final static String FIND_PRODUCT_BY_ID = "findProductById?";
    public final static String FIND_PRODUCT_BY_SC_ID = "findProductByScId?";
    public final static String FIND_PRODUCT_BY_KEY = "findProductByKey?";
    public final static String FIND_PRODUCT_BY_KEYWORD = "findProductByKeyword?";
    public final static String UPDATE_PRODUCT = "updateProduct?";
    public final static String INSERT_PRODUCT = "insertProduct?";
    public final static String DELETE_PRODUCT = "deleteProduct?";

    //pr
    public final static String FIND_PR_BY_DEAL_ID = "findPrsByDealId?";
    public final static String FIND_PR_BY_PRODUCT_ID = "findPrsByProductId?";
    public final static String FIND_PR_BY_USER_ID = "findPrsByUserId?";
    public final static String INSERT_PR = "insertPr?";

    //mc
    public final static String FIND_MC_BY_ID = "findMcById?";
    public final static String FIND_MCS = "findMcs?";
    public final static String INSERT_MC = "insertMc?";
    public final static String UPDATE_MC = "updateMc?";

    //sc
    public final static String FIND_SC_BY_ID = "findScById?";
    public final static String FIND_SC_BY_MC_ID = "findScByMcId?";
    public final static String INSERT_SC = "insertSc?";
    public final static String UPDATE_SC = "updateSc?";

    //hot sale
    public final static String FIND_HOT_SALE = "findHotSale?";

    //new product
    public final static String FIND_NEW_PRODUCT = "findNewProducts?";

    //special price
    public final static String FIND_SPECIAL_PRICE = "findSpecialPrice?";

    //withdraw 提现
    public final static String INSERT_WITHDRAW = "insertWithdraw?";
    public final static String FIND_WITHDRAW_MONEY_BY_USER_ID = "findWithdrawMoneyByUserId?";

    //search
    public final static String FIND_SEARCH_BY_USER_ID = "findSearchByUserId?";


    //product image
    public final static String FIND_PRODUCT_IMAGES_BY_PRODUCT_ID = "findImagesByProductId?";
    public final static String INSERT_PRODUCT_IMAGE = "insertProductImage?";

    //hot sale
    public final static String INSERT_HOTSALE = "insertHotSale?";
    public final static String INSERT_SPECIAL_PRICE = "insertSpecialPrice?";


    //backup
    public final static String BACKUP = "backup?";

    //recover
    public final static String RECOVER = "recover?";
}
