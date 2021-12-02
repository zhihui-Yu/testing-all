## rpc队列使用
请求/回复模式

客户端发起请求到队列，请求中包含一个属性(correlationId)
队列的信息，服务器处理完后就返回给一个reply_to的queue，然后queue返回客户处理结果
