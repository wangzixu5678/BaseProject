package com.example.hdxy.baseproject.Util;

/**
 * wechatShare(0);//分享到微信好友
 * wechatShare(1);//分享到微信朋友圈
 */
public class WxShareUtil {

//    private IWXAPI wxapi;
//    private static WxShareUtil instance;
//
//    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
//    private static final int THUMB_SIZE = 100; //缩略图大小
//    private static String appID;
//
//    private WxShareUtil(String appID) {
//        this.appID = appID;
//    }
//
//    public static WxShareUtil getInstance() {
//        if (instance == null) {
//            synchronized (WxShareUtil.class) {
//                if (instance == null) {
//                    if (appID == null) {
//			//获取清单文件中的APP_ID
//                        appID = WRKServiceMgr.getAppMetaData(WRKApplication.getContext(), WRKCommon.WX_APP_ID);
//                    }
//                    instance = new WxShareUtil(appID);
//                }
//            }
//        }
//        return instance;
//    }
//
//    public void regToWeiXin() {
//        wxapi = WXAPIFactory.createWXAPI(WRKApplication.getContext(), appID, true);
//        wxapi.registerApp(appID);
//    }
//
//    /**
//     * 判断是否安装微信
//     */
//    public boolean isWeiXinAppInstall() {
//        if (wxapi == null)
//            wxapi = WXAPIFactory.createWXAPI(WRKApplication.getContext(), appID);
//        if (wxapi.isWXAppInstalled()) {
//            return true;
//        } else {
//            Toast.makeText(WRKApplication.getContext(), R.string.no_install_wx, Toast.LENGTH_SHORT).show();
//            return false;
//        }
//    }
//
//    /**
//     * 是否支持分享到朋友圈
//     */
//    public boolean isWXAppSupportAPI() {
//        if (isWeiXinAppInstall()) {
//            int wxSdkVersion = wxapi.getWXAppSupportAPI();
//            if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    public String buildTransaction(String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//    }
//
//    public IWXAPI getWxApi() {
//        if (wxapi == null)
//            wxapi = WXAPIFactory.createWXAPI(WRKApplication.getContext(), appID);
//        return wxapi;
//    }
//
//    /**
//     * 分享文本类型
//     *
//     * @param text 文本内容
//     * @param type 微信会话或者朋友圈等
//     */
//    public void shareTextToWx(String text, int type) {
//        if (text == null || text.length() == 0) {
//            return;
//        }
//
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = text;
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        msg.description = text;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("text");
//        req.message = msg;
//        req.scene = type;
//
//        getWxApi().sendReq(req);
//    }
//
//    /**
//     * 分享图片到微信
//     */
//
//    public void shareImageToWx(final String imgUrl, String title, String desc, final int wxSceneSession) {
//        Bitmap bmp = BitmapFactory.decodeResource(WRKApplication.getContext().getResources(), R.mipmap.ic_launcher);
//        WXImageObject imgObj = new WXImageObject(bmp);
//
//        final WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = imgObj;
//        msg.title = title;
//        msg.description = desc;
//        final Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
//        bmp.recycle();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    InputStream imageStream = getImageStream(imgUrl);
//                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//                    bitmap.recycle();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                msg.thumbData = WRKFileUtil.bmpToByteArray(thumbBmp[0], true);
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("img");
//                req.message = msg;
//                req.scene = wxSceneSession;
//                getWxApi().sendReq(req);
//            }
//        }).start();
//    }
//
//    /**
//     * 分享音乐
//     *
//     * @param musicUrl       音乐资源地址
//     * @param title          标题
//     * @param desc           描述
//     * @param wxSceneSession
//     */
//    public void shareMusicToWx(final String musicUrl, final String title, final String desc, final String iconUrl, final int wxSceneSession) {
//        WXMusicObject music = new WXMusicObject();
//        music.musicUrl = musicUrl;
//
//        final WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = music;
//        msg.title = title;
//        msg.description = desc;
//
//        Bitmap bmp = BitmapFactory.decodeResource(WRKApplication.getContext().getResources(), R.mipmap.ic_launcher);
//        final Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
//        bmp.recycle();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    InputStream imageStream = getImageStream(iconUrl);
//                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//                    bitmap.recycle();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                msg.thumbData = WRKFileUtil.bmpToByteArray(thumbBmp[0], true);
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("music");
//                req.message = msg;
//                req.scene = wxSceneSession;
//                getWxApi().sendReq(req);
//            }
//        }).start();
//    }
//
//    /**
//     * 分享视频
//     *
//     * @param videoUrl       视频地址
//     * @param title          标题
//     * @param desc           描述
//     * @param wxSceneSession
//     */
//
//    public void shareVideoToWx(String videoUrl, String title, String desc, final String iconUrl, final int wxSceneSession) {
//        WXVideoObject video = new WXVideoObject();
//        video.videoUrl = videoUrl;
//
//        final WXMediaMessage msg = new WXMediaMessage(video);
//        msg.title = title;
//        msg.description = desc;
//        Bitmap bmp = BitmapFactory.decodeResource(WRKApplication.getContext().getResources(), R.mipmap.ic_launcher);
//        final Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
//        bmp.recycle();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    InputStream imageStream = getImageStream(iconUrl);
//                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//                    bitmap.recycle();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                msg.thumbData = WRKFileUtil.bmpToByteArray(thumbBmp[0], true);
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("video");
//                req.message = msg;
//                req.scene = wxSceneSession;
//                getWxApi().sendReq(req);
//            }
//        }).start();
//    }
//
//    /**
//     * 分享url地址
//     *
//     * @param url            地址
//     * @param title          标题
//     * @param desc           描述
//     * @param wxSceneSession 类型
//     */
//    public void shareUrlToWx(String url, String title, String desc, final String iconUrl, final int wxSceneSession) {
//
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//        final WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = title;
//
//        msg.description = desc;
//        Bitmap bmp = BitmapFactory.decodeResource(WRKApplication.getContext().getResources(), R.mipmap.ic_launcher);
//        final Bitmap[] thumbBmp = {Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true)};
//        bmp.recycle();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    InputStream imageStream = getImageStream(iconUrl);
//                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//                    thumbBmp[0] = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//                    bitmap.recycle();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                msg.thumbData = WRKFileUtil.bmpToByteArray(thumbBmp[0], true);
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
//                req.message = msg;
//                req.scene = wxSceneSession;
//                getWxApi().sendReq(req);
//            }
//        }).start();
//    }
//
//    /**
//     * 分享数据
//     *
//     * @param mShareData     分享数据
//     * @param context        上下文
//     * @param wxSceneSession 分享位置类型
//     */
//    public void shareToWx(ShareData mShareData, Context context, int wxSceneSession) {
//        if (mShareData != null) {
//            String title = mShareData.getTitle();
//            if (title != null && title.length() > 512)
//                title = title.substring(0, 511);
//            String desc = mShareData.getDesc();
//            if (desc != null && desc.length() > 1024)
//                desc = desc.substring(0, 1023);
//            String link = mShareData.getLink();
//            String dataUrl = mShareData.getData_url();
//            String imgUrl = mShareData.getImg_url();
//            String type = mShareData.getType();
//            if (TextUtils.isEmpty(type)) {
//                instance.shareUrlToWx(link, title, desc, imgUrl, wxSceneSession);
//                return;
//            }
//            switch (type) {
//                case "music":
//                    instance.shareMusicToWx(dataUrl, title, desc, imgUrl, wxSceneSession);
//                    break;
//                case "video":
//                    instance.shareVideoToWx(dataUrl, title, desc, imgUrl, wxSceneSession);
//                    break;
//                case "img":
//                    instance.shareImageToWx(imgUrl, title, desc, wxSceneSession);
//                    break;
//                case "link":
//                    instance.shareUrlToWx(link, title, desc, imgUrl, wxSceneSession);
//                    break;
//                default:
//                    instance.shareUrlToWx(link, title, desc, imgUrl, wxSceneSession);
//                    break;
//            }
//        } else {
//            Toast.makeText(context, "分享的数据为空，无法分享", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public InputStream getImageStream(String path) throws Exception {
//        URL url = new URL(path);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setConnectTimeout(5 * 1000);
//        conn.setRequestMethod("GET");
//        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//            return conn.getInputStream();
//        }
//        return null;
//    }
}
