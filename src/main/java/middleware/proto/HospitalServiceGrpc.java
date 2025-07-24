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
    comments = "Source: Hospital.proto")
public final class HospitalServiceGrpc {

  private HospitalServiceGrpc() {}

  public static final String SERVICE_NAME = "proto.HospitalService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<HospitalOuterClass.Empty,
          HospitalOuterClass.AvailableBedsReply> getGetAvailableBedsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAvailableBeds",
      requestType = HospitalOuterClass.Empty.class,
      responseType = HospitalOuterClass.AvailableBedsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<HospitalOuterClass.Empty,
          HospitalOuterClass.AvailableBedsReply> getGetAvailableBedsMethod() {
    io.grpc.MethodDescriptor<HospitalOuterClass.Empty, HospitalOuterClass.AvailableBedsReply> getGetAvailableBedsMethod;
    if ((getGetAvailableBedsMethod = HospitalServiceGrpc.getGetAvailableBedsMethod) == null) {
      synchronized (HospitalServiceGrpc.class) {
        if ((getGetAvailableBedsMethod = HospitalServiceGrpc.getGetAvailableBedsMethod) == null) {
          HospitalServiceGrpc.getGetAvailableBedsMethod = getGetAvailableBedsMethod = 
              io.grpc.MethodDescriptor.<HospitalOuterClass.Empty, HospitalOuterClass.AvailableBedsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.HospitalService", "getAvailableBeds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HospitalOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HospitalOuterClass.AvailableBedsReply.getDefaultInstance()))
                  .setSchemaDescriptor(new HospitalServiceMethodDescriptorSupplier("getAvailableBeds"))
                  .build();
          }
        }
     }
     return getGetAvailableBedsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<HospitalOuterClass.Hospital,
          HospitalOuterClass.HospitalsForRegionReply> getGetHospitalsForRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getHospitalsForRegion",
      requestType = HospitalOuterClass.Hospital.class,
      responseType = HospitalOuterClass.HospitalsForRegionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<HospitalOuterClass.Hospital,
          HospitalOuterClass.HospitalsForRegionReply> getGetHospitalsForRegionMethod() {
    io.grpc.MethodDescriptor<HospitalOuterClass.Hospital, HospitalOuterClass.HospitalsForRegionReply> getGetHospitalsForRegionMethod;
    if ((getGetHospitalsForRegionMethod = HospitalServiceGrpc.getGetHospitalsForRegionMethod) == null) {
      synchronized (HospitalServiceGrpc.class) {
        if ((getGetHospitalsForRegionMethod = HospitalServiceGrpc.getGetHospitalsForRegionMethod) == null) {
          HospitalServiceGrpc.getGetHospitalsForRegionMethod = getGetHospitalsForRegionMethod = 
              io.grpc.MethodDescriptor.<HospitalOuterClass.Hospital, HospitalOuterClass.HospitalsForRegionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.HospitalService", "getHospitalsForRegion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HospitalOuterClass.Hospital.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HospitalOuterClass.HospitalsForRegionReply.getDefaultInstance()))
                  .setSchemaDescriptor(new HospitalServiceMethodDescriptorSupplier("getHospitalsForRegion"))
                  .build();
          }
        }
     }
     return getGetHospitalsForRegionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HospitalServiceStub newStub(io.grpc.Channel channel) {
    return new HospitalServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HospitalServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HospitalServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HospitalServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HospitalServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class HospitalServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAvailableBeds(HospitalOuterClass.Empty request,
                                 io.grpc.stub.StreamObserver<HospitalOuterClass.AvailableBedsReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAvailableBedsMethod(), responseObserver);
    }

    /**
     */
    public void getHospitalsForRegion(HospitalOuterClass.Hospital request,
                                      io.grpc.stub.StreamObserver<HospitalOuterClass.HospitalsForRegionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetHospitalsForRegionMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAvailableBedsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                      HospitalOuterClass.Empty,
                      HospitalOuterClass.AvailableBedsReply>(
                  this, METHODID_GET_AVAILABLE_BEDS)))
          .addMethod(
            getGetHospitalsForRegionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                      HospitalOuterClass.Hospital,
                      HospitalOuterClass.HospitalsForRegionReply>(
                  this, METHODID_GET_HOSPITALS_FOR_REGION)))
          .build();
    }
  }

  /**
   */
  public static final class HospitalServiceStub extends io.grpc.stub.AbstractStub<HospitalServiceStub> {
    private HospitalServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HospitalServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected HospitalServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HospitalServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAvailableBeds(HospitalOuterClass.Empty request,
                                 io.grpc.stub.StreamObserver<HospitalOuterClass.AvailableBedsReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAvailableBedsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getHospitalsForRegion(HospitalOuterClass.Hospital request,
                                      io.grpc.stub.StreamObserver<HospitalOuterClass.HospitalsForRegionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetHospitalsForRegionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HospitalServiceBlockingStub extends io.grpc.stub.AbstractStub<HospitalServiceBlockingStub> {
    private HospitalServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HospitalServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected HospitalServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HospitalServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public HospitalOuterClass.AvailableBedsReply getAvailableBeds(HospitalOuterClass.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetAvailableBedsMethod(), getCallOptions(), request);
    }

    /**
     */
    public HospitalOuterClass.HospitalsForRegionReply getHospitalsForRegion(HospitalOuterClass.Hospital request) {
      return blockingUnaryCall(
          getChannel(), getGetHospitalsForRegionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HospitalServiceFutureStub extends io.grpc.stub.AbstractStub<HospitalServiceFutureStub> {
    private HospitalServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HospitalServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected HospitalServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HospitalServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<HospitalOuterClass.AvailableBedsReply> getAvailableBeds(
        HospitalOuterClass.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAvailableBedsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<HospitalOuterClass.HospitalsForRegionReply> getHospitalsForRegion(
        HospitalOuterClass.Hospital request) {
      return futureUnaryCall(
          getChannel().newCall(getGetHospitalsForRegionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_AVAILABLE_BEDS = 0;
  private static final int METHODID_GET_HOSPITALS_FOR_REGION = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HospitalServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HospitalServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_AVAILABLE_BEDS:
          serviceImpl.getAvailableBeds((HospitalOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<HospitalOuterClass.AvailableBedsReply>) responseObserver);
          break;
        case METHODID_GET_HOSPITALS_FOR_REGION:
          serviceImpl.getHospitalsForRegion((HospitalOuterClass.Hospital) request,
              (io.grpc.stub.StreamObserver<HospitalOuterClass.HospitalsForRegionReply>) responseObserver);
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

  private static abstract class HospitalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HospitalServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return HospitalOuterClass.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HospitalService");
    }
  }

  private static final class HospitalServiceFileDescriptorSupplier
      extends HospitalServiceBaseDescriptorSupplier {
    HospitalServiceFileDescriptorSupplier() {}
  }

  private static final class HospitalServiceMethodDescriptorSupplier
      extends HospitalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HospitalServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (HospitalServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HospitalServiceFileDescriptorSupplier())
              .addMethod(getGetAvailableBedsMethod())
              .addMethod(getGetHospitalsForRegionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
