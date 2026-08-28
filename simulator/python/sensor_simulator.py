import random
import time
from dataclasses import dataclass

import requests


API_URL = "http://localhost:8080/api/v1/observations"
SEND_INTERVAL_SECONDS = 1


@dataclass
class TargetState:
    target_id: str
    x: float
    y: float
    velocity_x: float
    velocity_y: float


class SensorSimulator:
    def __init__(self, sensor_id: str, target: TargetState):
        self.sensor_id = sensor_id
        self.target = target
        self.observation_number = 0

    def update_target_position(self):
        self.target.x += self.target.velocity_x * SEND_INTERVAL_SECONDS
        self.target.y += self.target.velocity_y * SEND_INTERVAL_SECONDS

    def create_observation(self):
        self.observation_number += 1

        # Simulated sensor measurement noise
        measured_x = self.target.x + random.gauss(0, 2.0)
        measured_y = self.target.y + random.gauss(0, 2.0)

        measured_velocity_x = (
            self.target.velocity_x + random.gauss(0, 0.25)
        )

        measured_velocity_y = (
            self.target.velocity_y + random.gauss(0, 0.25)
        )

        confidence = random.uniform(0.88, 0.99)

        return {
            "sensorId": self.sensor_id,
            "targetId": self.target.target_id,
            "x": round(measured_x, 2),
            "y": round(measured_y, 2),
            "velocityX": round(measured_velocity_x, 2),
            "velocityY": round(measured_velocity_y, 2),
            "confidence": round(confidence, 2),
        }

    def send_observation(self, observation):
        try:
            response = requests.post(
                API_URL,
                json=observation,
                timeout=5
            )

            if response.status_code == 201:
                print("✓ Observation accepted")
            else:
                print(
                    f"✗ API returned HTTP {response.status_code}: "
                    f"{response.text}"
                )

        except requests.exceptions.ConnectionError:
            print("✗ Cannot connect to sensor-ingestion service.")
            print("  Make sure Spring Boot is running on port 8080.")

        except requests.exceptions.RequestException as error:
            print(f"✗ Request failed: {error}")

    def print_observation(self, observation):
        print()
        print(f"Observation #{self.observation_number:03}")
        print(f"Sensor:      {observation['sensorId']}")
        print(f"Target:      {observation['targetId']}")
        print(
            f"Position:    "
            f"({observation['x']}, {observation['y']})"
        )
        print(
            f"Velocity:    "
            f"({observation['velocityX']}, "
            f"{observation['velocityY']})"
        )
        print(f"Confidence:  {observation['confidence']}")
        print("Sending to Java ingestion service...")

    def run(self):
        print("=" * 55)
        print("AegisSim Distributed Sensor Simulator")
        print("=" * 55)
        print(f"Sensor ID: {self.sensor_id}")
        print(f"Target ID: {self.target.target_id}")
        print(f"API:       {API_URL}")
        print()
        print("Press Ctrl+C to stop.")
        print("=" * 55)

        try:
            while True:
                self.update_target_position()

                observation = self.create_observation()

                self.print_observation(observation)

                self.send_observation(observation)

                time.sleep(SEND_INTERVAL_SECONDS)

        except KeyboardInterrupt:
            print()
            print("Sensor simulator stopped.")


def main():
    target = TargetState(
        target_id="TARGET-001",
        x=100.0,
        y=200.0,
        velocity_x=12.0,
        velocity_y=4.0,
    )

    simulator = SensorSimulator(
        sensor_id="RADAR-SIM-01",
        target=target,
    )

    simulator.run()


if __name__ == "__main__":
    main()