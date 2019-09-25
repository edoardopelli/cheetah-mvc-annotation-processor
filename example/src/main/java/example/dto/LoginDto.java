package example.dto;

import org.cheetah.spring.annotations.CheetahMapping;
import org.cheetah.spring.annotations.CheetahSpring;

import example.domain.Login;

@CheetahSpring(httprest="/login",pkgroot="example", entity="example.domain.Login", mappings= {
		@CheetahMapping(source="idUtente.idUtente", target="idUtente")
})
public class LoginDto {

}
