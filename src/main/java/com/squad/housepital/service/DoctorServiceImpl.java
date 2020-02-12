package com.squad.housepital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.housepital.constant.Constant;
import com.squad.housepital.dto.AvailableSlotDto;
import com.squad.housepital.dto.DoctorDto;
import com.squad.housepital.dto.LoginRequestDto;
import com.squad.housepital.dto.LoginResponseDto;
import com.squad.housepital.dto.SlotDto;
import com.squad.housepital.entity.Doctor;
import com.squad.housepital.entity.DoctorSlot;
import com.squad.housepital.exception.DoctorNotFoundException;
import com.squad.housepital.exception.SlotNotFoundException;
import com.squad.housepital.repository.DoctorRepository;
import com.squad.housepital.repository.DoctorSlotRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	DoctorSlotRepository doctorSlotRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-12. This method will authenticate the user.
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
	 * @since 2020-02-12. this method will get the details of the doctor.
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
	 * @since 2020-02-12. this method will get the booked slots for the doctor on
	 *        the doctor side.
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
