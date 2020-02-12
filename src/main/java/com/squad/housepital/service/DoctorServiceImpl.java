package com.squad.housepital.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.AppointmentRequestDto;
import com.squad.housepital.dto.AvailableSlotDto;
import com.squad.housepital.dto.DoctorDto;
import com.squad.housepital.dto.LoginRequestDto;
import com.squad.housepital.dto.LoginResponseDto;
import com.squad.housepital.dto.ResponseDto;
import com.squad.housepital.dto.SlotDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.entity.Hospital;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.HospitalNotFoundException;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.repository.DoctorRepository;
import com.squad.housepital.repository.DoctorSlotRepository;
import com.squad.housepital.repository.HospitalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	HospitalRepository hospitalRepository;

	@Autowired
	DoctorSlotRepository doctorSlotRepository;

	/**
	 * This method is used to add the appointment slot for doctor on any particular
	 * day based on doctor availability
	 * 
	 * @author Chethana M
	 * @param appointmentRequestDto- Takes appointment Details to be persisted
	 * @return ResponseDto - Returns success/failure status code with message
	 * @throws DoctorNotFoundException   - thrown when Parsed Doctor Id is invalid
	 * @throws HospitalNotFoundException - thrown when Parsed HospitalId is invalid
	 * @since Feb-12-2020
	 */
	public ResponseDto addAppointmentSlot(AppointmentRequestDto appointmentRequestDto)
			throws DoctorNotFoundException, HospitalNotFoundException {
		log.info("Entering into addAppointmentSlot of DoctorServiceImpl");

		Optional<Doctor> doctorResponse = doctorRepository.findById(appointmentRequestDto.getDoctorId());
		if (!doctorResponse.isPresent()) {
			log.error("Exception occured in addAppointmentSlot:"+Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		Optional<Hospital> hospitalResponse = hospitalRepository.findById(appointmentRequestDto.getHospitalId());
		if (!hospitalResponse.isPresent()) {
			log.error("Exception occured in addAppointmentSlot:"+Constant.HOSPITAL_NOT_FOUND);
			throw new HospitalNotFoundException(Constant.HOSPITAL_NOT_FOUND);
		}
		//List<DoctorSlot> doctorSlotList = new ArrayList<>();
		LocalTime fromTime = LocalTime.parse(appointmentRequestDto.getFromTime());
		LocalTime toTime = LocalTime.parse(appointmentRequestDto.getSlotToTime());
		while (fromTime.isBefore(toTime)) {
			Optional<DoctorSlot> doctorSlotResponse=doctorSlotRepository.findByDoctorAndDateAndSlotTime(doctorResponse.get(),appointmentRequestDto.getDate(),fromTime);
			
			if(doctorSlotResponse.isPresent()) {
				log.debug("Skipping the doctorSlot detaild:Record already exist");
			}
			else {
			DoctorSlot doctorSlot = new DoctorSlot();
			doctorSlot.setAvailability(Constant.AVAILABLE);
			doctorSlot.setDate(appointmentRequestDto.getDate());
			doctorSlot.setDoctor(doctorResponse.get());
			doctorSlot.setHospital(hospitalResponse.get());
			doctorSlot.setSlotTime(fromTime);
			doctorSlotRepository.save(doctorSlot);
			}
			fromTime = fromTime.plusMinutes(Constant.SLOT_INTERVAL);
			
		}
		log.debug("Exiting form addAppointmentSlot of DoctorServiceImpl");
		return new ResponseDto();
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12.
	 * 
	 *        This method will authenticate the user.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message,statusCode and
	 *         doctorDetails
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	@Override
	public LoginResponseDto authenticateDoctor(LoginRequestDto loginRequestDto) throws DoctorNotFoundException {
		Optional<Doctor> doctor = doctorRepository.findByMobileAndPassword(loginRequestDto.getMobile(),
				loginRequestDto.getPassword());
		if (!doctor.isPresent()) {
			log.error("Entering into UserServiceImpl authenticateUser method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setDoctorId(doctor.get().getDoctorId());
		loginResponseDto.setDoctorName(doctor.get().getDoctorName());
		log.info("Authentication Successful");
		return loginResponseDto;

	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12. This method will get the details of the doctor.
	 * @param doctorId - unique id of doctor
	 * @return DoctorDto which has doctorDetails
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	public DoctorDto getDoctorDetails(Integer doctorId) throws DoctorNotFoundException {
		Optional<Doctor> doctor = doctorRepository.findById(doctorId);
		if (!doctor.isPresent()) {
			log.debug("Exception occurred in DoctorServiceImpl getDoctorDetails method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		log.info("Entering into DoctorServiceImpl getDoctorDetails method: getting doctor details");
		DoctorDto doctorDto = new DoctorDto();
		BeanUtils.copyProperties(doctor.get(), doctorDto);
		return doctorDto;
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12. this method will get the available slots for the doctor on
	 *        the patient side.
	 * @param doctorId - unique id of doctor
	 * @return list of SlotDto which has available slots and details.
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	public List<SlotDto> getSlotsForPatient(Integer doctorId) throws DoctorNotFoundException, SlotNotFoundException {
		Optional<Doctor> doctor = doctorRepository.findById(doctorId);
		if (!doctor.isPresent()) {
			log.debug("Exception occurred in DoctorServiceImpl getSlotsForPatient method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		List<DoctorSlot> doctorSlots = doctorSlotRepository.findByDoctorAndAvailability(doctor.get(),
				Constant.AVAILABLE);
		if (doctorSlots.isEmpty()) {
			log.debug("Exception occurred in DoctorServiceImpl getSlotsForPatient method:" + Constant.SLOT_NOT_FOUND);
			throw new SlotNotFoundException(Constant.SLOT_NOT_FOUND);
		}
		log.debug("Entering into DoctorServiceImpl getSlotsForPatient method: getting available slots");
		List<SlotDto> slotList = new ArrayList<>();
		doctorSlots.forEach(slots -> {
			SlotDto slotDto = new SlotDto();
			slotDto.setDate(slots.getDate());
			slotDto.setHospitalId(slots.getHospital().getHospitalId());
			slotDto.setHospitalName(slots.getHospital().getHospitalName());
			slotDto.setSlotTime(slots.getSlotTime());
			slotDto.setDoctorSlotId(slots.getDoctorSlotId());
			slotList.add(slotDto);
		});

		return slotList;

	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12.
	 * 
	 * This method will get the booked slots for the doctor on the doctor
	 *        side.
	 * @param doctorId - unique id of doctor
	 * @return list of AvailableSlotDto which has booked slots and details.
	 * @throws DoctorNotFoundException it will throw the exception if the doctor is
	 *                                 not registered.
	 * 
	 */
	public List<AvailableSlotDto> getSlotsForDoctor(Integer doctorId)
			throws DoctorNotFoundException, SlotNotFoundException {
		Optional<Doctor> doctor = doctorRepository.findById(doctorId);
		if (!doctor.isPresent()) {
			log.debug("Exception occurred in DoctorServiceImpl getSlotsForDoctor method:" + Constant.DOCTOR_NOT_FOUND);
			throw new DoctorNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}
		List<DoctorSlot> doctorSlots = doctorSlotRepository.findByDoctorAndAvailability(doctor.get(),
				Constant.UN_AVAILABLE);
		if (doctorSlots.isEmpty()) {
			log.debug("Exception occurred in DoctorServiceImpl getSlotsForDoctor method:" + Constant.SLOT_NOT_FOUND);
			throw new SlotNotFoundException(Constant.SLOT_NOT_FOUND);
		}
		log.debug("Entering into DoctorServiceImpl getSlotsForDoctor method: getting booked slots");
		List<AvailableSlotDto> slotList = new ArrayList<>();
		doctorSlots.forEach(slots -> {
			AvailableSlotDto availableSlotDto = new AvailableSlotDto();
			availableSlotDto.setDate(slots.getDate());
			availableSlotDto.setHospitalId(slots.getHospital().getHospitalId());
			availableSlotDto.setHospitalName(slots.getHospital().getHospitalName());
			availableSlotDto.setSlotTime(slots.getSlotTime());
			availableSlotDto.setPatientId(slots.getPatient().getPatientId());
			availableSlotDto.setPatientName(slots.getPatient().getPatientName());
			availableSlotDto.setMobile(slots.getPatient().getMobile());
			availableSlotDto.setEmail(slots.getPatient().getEmail());
			slotList.add(availableSlotDto);
		});

		return slotList;

	}

}
