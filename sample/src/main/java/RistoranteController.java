

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dnp.fastbook.dto.RistoranteDto;
import com.dnp.fastbook.service.RistoranteService;

@RestController("/ristorante")
public class RistoranteController {

	@Autowired
	RistoranteService service;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RistoranteDto> save(@RequestBody RistoranteDto dto) {
		dto = service.save(dto);
		if (dto.getIdRistorante() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
		return ResponseEntity.ok(dto);
	}
	@PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RistoranteDto> update(@RequestBody RistoranteDto dto) {
		dto = service.save(dto);
		if (dto.getIdRistorante() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
		}
		return ResponseEntity.ok(dto);
	}

	@PostMapping(path = "/saveall", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Iterable<RistoranteDto>> saveAll(@RequestBody Iterable<RistoranteDto> dtos) {
		dtos = service.saveAll(dtos);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping(path="/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<RistoranteDto> findById(@PathVariable("id") java.lang.Long id) {
		try {
			RistoranteDto dto = service.findById(id);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteById(@PathVariable("id") java.lang.Long id) {
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}



	@GetMapping(path="/findall/{page}/{max}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<RistoranteDto>> findAll(@PathVariable("page") int page,@PathVariable("max") int maxResult) {
		List<RistoranteDto> dtos = service.findAll(page, maxResult);
		if(dtos==null || dtos.size()==0) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dtos);
	}

	
}