package middleware.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: application.middleware.proto.Bully.proto")
public final class BullyServiceGrpc {

  private BullyServiceGrpc() {}

  public static final String SERVICE_NAME = "BullyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Bully.ElectionRequest,
      Bully.ElectionResponse> getStartElectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartElection",
      requestType = Bully.ElectionRequest.class,
      responseType = Bully.ElectionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Bully.ElectionRequest,
      Bully.ElectionResponse> getStartElectionMethod() {
    io.grpc.MethodDescriptor<Bully.ElectionRequest, Bully.ElectionResponse> getStartElectionMethod;
    if ((getStartElectionMethod = BullyServiceGrpc.getStartElectionMethod) == null) {
      synchronized (BullyServiceGrpc.class) {
        if ((getStartElectionMethod = BullyServiceGrpc.getStartElectionMethod) == null) {
          BullyServiceGrpc.getStartElectionMethod = getStartElectionMethod = 
              io.grpc.MethodDescriptor.<Bully.ElectionRequest, Bully.ElectionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "BullyService", "StartElection"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Bully.ElectionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Bully.ElectionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BullyServiceMethodDescriptorSupplier("StartElection"))
                  .build();
          }
        }
     }
     return getStartElectionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Bully.CoordinatorRequest,
      Bully.CoordinatorResponse> getSendCoordinatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendCoordinator",
      requestType = Bully.CoordinatorRequest.class,
      responseType = Bully.CoordinatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Bully.CoordinatorRequest,
      Bully.CoordinatorResponse> getSendCoordinatorMethod() {
    io.grpc.MethodDescriptor<Bully.CoordinatorRequest, Bully.CoordinatorResponse> getSendCoordinatorMethod;
    if ((getSendCoordinatorMethod = BullyServiceGrpc.getSendCoordinatorMethod) == null) {
      synchronized (BullyServiceGrpc.class) {
        if ((getSendCoordinatorMethod = BullyServiceGrpc.getSendCoordinatorMethod) == null) {
          BullyServiceGrpc.getSendCoordinatorMethod = getSendCoordinatorMethod = 
              io.grpc.MethodDescriptor.<Bully.CoordinatorRequest, Bully.CoordinatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "BullyService", "SendCoordinator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Bully.CoordinatorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Bully.CoordinatorResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BullyServiceMethodDescriptorSupplier("SendCoordinator"))
                  .build();
          }
        }
     }
     return getSendCoordinatorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BullyServiceStub newStub(io.grpc.Channel channel) {
    return new BullyServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BullyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BullyServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BullyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BullyServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class BullyServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void startElection(Bully.ElectionRequest request,
        io.grpc.stub.StreamObserver<Bully.ElectionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartElectionMethod(), responseObserver);
    }

    /**
     */
    public void sendCoordinator(Bully.CoordinatorRequest request,
        io.grpc.stub.StreamObserver<Bully.CoordinatorResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSendCoordinatorMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStartElectionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Bully.ElectionRequest,
                Bully.ElectionResponse>(
                  this, METHODID_START_ELECTION)))
          .addMethod(
            getSendCoordinatorMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Bully.CoordinatorRequest,
                Bully.CoordinatorResponse>(
                  this, METHODID_SEND_COORDINATOR)))
          .build();
    }
  }

  /**
   */
  public static final class BullyServiceStub extends io.grpc.stub.AbstractStub<BullyServiceStub> {
    private BullyServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BullyServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BullyServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BullyServiceStub(channel, callOptions);
    }

    /**
     */
    public void startElection(Bully.ElectionRequest request,
        io.grpc.stub.StreamObserver<Bully.ElectionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartElectionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendCoordinator(Bully.CoordinatorRequest request,
        io.grpc.stub.StreamObserver<Bully.CoordinatorResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendCoordinatorMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BullyServiceBlockingStub extends io.grpc.stub.AbstractStub<BullyServiceBlockingStub> {
    private BullyServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BullyServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BullyServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BullyServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Bully.ElectionResponse startElection(Bully.ElectionRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartElectionMethod(), getCallOptions(), request);
    }

    /**
     */
    public Bully.CoordinatorResponse sendCoordinator(Bully.CoordinatorRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendCoordinatorMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BullyServiceFutureStub extends io.grpc.stub.AbstractStub<BullyServiceFutureStub> {
    private BullyServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BullyServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected BullyServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BullyServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Bully.ElectionResponse> startElection(
        Bully.ElectionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStartElectionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Bully.CoordinatorResponse> sendCoordinator(
        Bully.CoordinatorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendCoordinatorMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_START_ELECTION = 0;
  private static final int METHODID_SEND_COORDINATOR = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BullyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BullyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_START_ELECTION:
          serviceImpl.startElection((Bully.ElectionRequest) request,
              (io.grpc.stub.StreamObserver<Bully.ElectionResponse>) responseObserver);
          break;
        case METHODID_SEND_COORDINATOR:
          serviceImpl.sendCoordinator((Bully.CoordinatorRequest) request,
              (io.grpc.stub.StreamObserver<Bully.CoordinatorResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BullyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BullyServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Bully.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BullyService");
    }
  }

  private static final class BullyServiceFileDescriptorSupplier
      extends BullyServiceBaseDescriptorSupplier {
    BullyServiceFileDescriptorSupplier() {}
  }

  private static final class BullyServiceMethodDescriptorSupplier
      extends BullyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BullyServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BullyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BullyServiceFileDescriptorSupplier())
              .addMethod(getStartElectionMethod())
              .addMethod(getSendCoordinatorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
