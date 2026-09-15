package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MainTeleOp")
public class MainTeleOp extends LinearOpMode{

  private DcMotor frontLeft;
  private DcMotor backLeft;
  private DcMotor frontRight;
  private DcMotor backRight;


  @Override
  public void runOpMode() {
    frontLeft = hardwareMap.get(DcMotor.class,"frontLeft");
    frontRight = hardwareMap.get(DcMotor.class,"frontRight");
    backLeft = hardwareMap.get(DcMotor.class,"backLeft");
    backRight = hardwareMap.get(DcMotor.class,"backRight");

    //because motors mounted on opposite sides physically oppose each other.
    frontRight.setDirection(DcMotor.Direction.REVERSE);
    backRight.setDirection(DcMotor.Direction.REVERSE);

    //motors resist movement when power is 0
    frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    //telemetry update
    telemetry.addData("Status:","initialized");
    telemetry.update();

    waitForStart();

    //runs until stop is pressed
    while (opModeIsActive()){
      //left stick controls forwards/backwards
      double drive = -gamepad1.left_stick_y;

      //right stick controls turning
      double turn = gamepad1.right_stick_x;

      double leftPower = drive + turn;
      double rightPower = drive - turn;

      //slow mode by holding left bumper
      double speedMultiplier = 1.0;
      if (gamepad1.left_bumper){
        speedMultiplier = 0.4;
      }
      leftPower *= speedMultiplier;
      rightPower *= speedMultiplier;

      //prevent calculated motor powers from going above 1 or below -1
      double max = Math.max(
              Math.abs(leftPower),
              Math.abs(rightPower)
      );

      if (max>1.0){
        leftPower /= max;
        rightPower /= max;
      }

      //send calculated power to drivetrain motors
      frontLeft.setPower(leftPower);
      backLeft.setPower(leftPower);

      frontRight.setPower(rightPower);
      backRight.setPower(rightPower);

      //telemetry
      telemetry.addData("Drive", drive);
      telemetry.addData("Turn", turn);
      telemetry.addData("Left Power", leftPower);
      telemetry.addData("Right Power", rightPower);
      telemetry.addData("Slow Mode", gamepad1.left_bumper);
      telemetry.addData("Status", "Running");
      telemetry.update();
    }
  }
}
